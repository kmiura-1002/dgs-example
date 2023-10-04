package dgs.example.datafetchers

import com.netflix.dgs.codegen.generated.DgsConstants
import com.netflix.dgs.codegen.generated.types.CreateTodoInput
import com.netflix.dgs.codegen.generated.types.CreateTodoPayload
import com.netflix.dgs.codegen.generated.types.DeleteTodoPayload
import com.netflix.dgs.codegen.generated.types.UpdateTodoInput
import com.netflix.dgs.codegen.generated.types.UpdateTodoPayload
import com.netflix.dgs.codegen.generated.types.UpdateTodoStatusInput
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import dgs.example.services.ITodosService
import dgs.example.value.TodoId


@DgsComponent
class TodoMutation(
    private val service: ITodosService
) {

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.CreateTodo)
    fun createTodo(
        @InputArgument(DgsConstants.MUTATION.CREATETODO_INPUT_ARGUMENT.Input) input: CreateTodoInput
    ): CreateTodoPayload {
        return CreateTodoPayload(
            service.addTodo(input)
        )
    }

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.DeleteTodo)
    fun deleteTodo(@InputArgument id: Long): DeleteTodoPayload {
        val deletedTodo = service.deleteTodo(TodoId(id))
        return DeleteTodoPayload(
            deletedTodo
        )
    }

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.UpdateTodo)
    fun updateTodo(
        @InputArgument(DgsConstants.MUTATION.UPDATETODO_INPUT_ARGUMENT.Input) input: UpdateTodoInput
    ): UpdateTodoPayload {

        return UpdateTodoPayload(
            service.updateTodo(input)
        )
    }

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.UpdateTodoStatus)
    fun updateTodoStatus(
        @InputArgument(
            DgsConstants.MUTATION.UPDATETODOSTATUS_INPUT_ARGUMENT.Input
        ) input: UpdateTodoStatusInput
    ): UpdateTodoPayload {
        return UpdateTodoPayload(
            service.updateStatus(input)
        )
    }
}