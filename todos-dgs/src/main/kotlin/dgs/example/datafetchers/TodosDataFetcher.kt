package dgs.example.datafetchers

import com.netflix.dgs.codegen.generated.types.TodoConnection
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import dgs.example.services.ITodosService
import kotlinx.coroutines.coroutineScope

@DgsComponent
class TodosDataFetcher(private val todosService: ITodosService) {

    @DgsQuery
    suspend fun todos(first: Int?, after: String?, last: Int?, before: String?, query: String?): TodoConnection =
        coroutineScope {
            todosService.todos()
        }
}