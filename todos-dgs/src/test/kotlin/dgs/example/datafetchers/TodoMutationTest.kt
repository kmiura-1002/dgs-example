package dgs.example.datafetchers

import com.netflix.dgs.codegen.generated.client.DeleteTodoGraphQLQuery
import com.netflix.dgs.codegen.generated.client.DeleteTodoProjectionRoot
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import dgs.example.exception.MyException
import dgs.example.scalars.DateTimeScalarRegistration
import dgs.example.services.ITodosService
import dgs.example.value.TodoId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest(classes = [DgsAutoConfiguration::class, TodoMutation::class, DateTimeScalarRegistration::class])
class TodoMutationTest {
    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @MockBean
    lateinit var todosService: ITodosService

    @Test
    fun `Error thrown when deleting`() {
        `when`(
            todosService.deleteTodo(
                any(TodoId::class.java) ?: TodoId(1)
            )
        ).thenThrow(MyException("Test Throw Exception!"))
        val graphQLQueryRequest = GraphQLQueryRequest(
            DeleteTodoGraphQLQuery.Builder().id("1").build(),
            DeleteTodoProjectionRoot<Nothing, Nothing>().deletedTodo().title()
        )

        val result = dgsQueryExecutor.execute(graphQLQueryRequest.serialize())

        assertThat(result.errors).isNotEmpty;
        assertThat(result.errors[0].message).isEqualTo("Test Throw Exception!");

    }
}