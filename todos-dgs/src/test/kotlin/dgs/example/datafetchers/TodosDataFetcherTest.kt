package dgs.example.datafetchers

import com.netflix.dgs.codegen.generated.client.TodosGraphQLQuery
import com.netflix.dgs.codegen.generated.client.TodosProjectionRoot
import com.netflix.dgs.codegen.generated.types.Star
import com.netflix.dgs.codegen.generated.types.Todo
import com.netflix.dgs.codegen.generated.types.TodoStatus
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import dgs.example.scalars.DateTimeScalarRegistration
import dgs.example.services.ITodosService
import dgs.example.value.TodoId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.OffsetDateTime

@SpringBootTest(classes = [DgsAutoConfiguration::class, TodosDataFetcher::class, DateTimeScalarRegistration::class])
class TodosDataFetcherTest {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @MockBean
    lateinit var todosService: ITodosService

    @BeforeEach
    fun before() {
        Mockito.`when`(todosService.todos()).thenAnswer {
            listOf(
                Todo(
                    id = TodoId(1).nodeId(),
                    title = "test title",
                    details = "todo",
                    status = TodoStatus.inprogress,
                    dueDate = null,
                    updateAt = OffsetDateTime.now(),
                    createAt = OffsetDateTime.now(),
                    star = Star(0)
                )
            )

        }
    }

    @Test
    fun `Queries for todos`() {
        val titles: List<String> = dgsQueryExecutor.executeAndExtractJsonPath(
            """
            {
                todos {
                    title
                }
            }
        """.trimIndent(), "data.todos[*].title"
        )

        assertThat(titles).contains("test title")
    }

    @Test
    fun todos_use_client() {
        val graphQLQueryRequest = GraphQLQueryRequest(
            TodosGraphQLQuery.Builder().build(),
            TodosProjectionRoot<Nothing, Nothing>().title()
        )

        val titles: List<String> =
            dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest.serialize(), "data.todos[*].title")
        assertThat(titles).containsExactly("test title")
    }
}