package dgs.example

import graphql.execution.instrumentation.Instrumentation
import graphql.execution.instrumentation.tracing.TracingInstrumentation
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class UserApplication {
    @Bean
    @ConditionalOnProperty(prefix = "graphql.tracing", name = ["enabled"], matchIfMissing = true)
    fun tracingInstrumentation(): Instrumentation? {
        return TracingInstrumentation()
    }
}

fun main(args: Array<String>) {
    runApplication<UserApplication>(*args)
}
