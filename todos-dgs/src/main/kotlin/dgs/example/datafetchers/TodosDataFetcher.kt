package dgs.example.datafetchers

import com.netflix.dgs.codegen.generated.DgsConstants
import com.netflix.dgs.codegen.generated.types.Star
import com.netflix.dgs.codegen.generated.types.Todo
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
import dgs.example.services.ITodosService
import kotlinx.coroutines.coroutineScope

@DgsComponent
class TodosDataFetcher(private val todosService: ITodosService) {

    @DgsQuery
    suspend fun todos(first: Int?, after: String?, last: Int?, before: String?, query: String?): List<Todo> =
        coroutineScope {
            todosService.todos()
        }

    @DgsData(parentType = DgsConstants.TODO.TYPE_NAME, field = DgsConstants.TODO.Star)
    fun reviews(dfe: DgsDataFetchingEnvironment): Star {
        val todo = dfe.getSource<Todo>()
        // fetch Stars
        return Star(999)
    }
}