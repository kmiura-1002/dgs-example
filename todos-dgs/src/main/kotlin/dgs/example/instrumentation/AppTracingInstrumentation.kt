package dgs.example.instrumentation

import dgs.example.extensions.logger.logger
import graphql.ExecutionResult
import graphql.execution.DataFetcherResult
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.InstrumentationState
import graphql.execution.instrumentation.SimplePerformantInstrumentation
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters
import graphql.schema.DataFetcher
import graphql.schema.GraphQLNonNull
import graphql.schema.GraphQLObjectType
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class AppTracingInstrumentation : SimplePerformantInstrumentation() {

    override fun createState(): InstrumentationState {
        return TraceState()
    }

    override fun beginExecution(parameters: InstrumentationExecutionParameters): InstrumentationContext<ExecutionResult> {
        val state: TraceState = parameters.getInstrumentationState()
        state.traceStartTime = System.currentTimeMillis()

        return super.beginExecution(parameters)
    }

    override fun instrumentDataFetcher(
        dataFetcher: DataFetcher<*>,
        parameters: InstrumentationFieldFetchParameters,
        state: InstrumentationState?
    ): DataFetcher<*> {
        return instrumentDataFetcher(dataFetcher, parameters)
    }

    override fun instrumentDataFetcher(
        dataFetcher: DataFetcher<*>,
        parameters: InstrumentationFieldFetchParameters
    ): DataFetcher<*> {
        if (parameters.isTrivialDataFetcher) {
            return dataFetcher
        }

        val dataFetcherName = findDatafetcherTag(parameters)
        logger.debug("Query arguments: ${parameters.executionStepInfo.parent.arguments}")
        return DataFetcher { environment ->
            val startTime = System.currentTimeMillis()
            val result = kotlin.runCatching { dataFetcher.get(environment) }.getOrElse {
                throw it.cause ?: it
            }
            if (result is CompletableFuture<*>) {
                result.whenComplete { _, _ ->
                    val totalTime = System.currentTimeMillis() - startTime
                    logger.info("Async datafetcher '$dataFetcherName' took ${totalTime}ms")
                }
            } else {
                val totalTime = System.currentTimeMillis() - startTime
                logger.info("Datafetcher '$dataFetcherName': ${totalTime}ms")
            }

            if(result is DataFetcherResult<*> && result.errors.isNotEmpty()){
                result.errors.forEach { logger.warn("${it.message} ${it.errorType} ${it.locations}") }
            }

            result
        }
    }

    override fun instrumentExecutionResult(
        executionResult: ExecutionResult,
        parameters: InstrumentationExecutionParameters
    ): CompletableFuture<ExecutionResult> {
        val state: TraceState = parameters.getInstrumentationState()
        val totalTime = System.currentTimeMillis() - state.traceStartTime

        logger.info("Total execution time: ${parameters.executionInput.operationName} ${totalTime}ms")

        return super.instrumentExecutionResult(executionResult, parameters)
    }

    private fun findDatafetcherTag(parameters: InstrumentationFieldFetchParameters): String {
        val type = parameters.executionStepInfo.parent.type
        val parentType = if (type is GraphQLNonNull) {
            type.wrappedType as GraphQLObjectType
        } else {
            type as GraphQLObjectType
        }

        return "${parentType.name}.${parameters.executionStepInfo.path.segmentName}"
    }

    data class TraceState(var traceStartTime: Long = 0) : InstrumentationState
}