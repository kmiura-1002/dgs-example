package dgs.example.datafetchers

import com.netflix.dgs.codegen.generated.types.Todo
import com.netflix.dgs.codegen.generated.types.UserInfo
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsEntityFetcher
import dgs.example.dataloaders.UserDataLoaderByTodoId
import dgs.example.value.TodoId
import org.dataloader.DataLoader
import java.util.concurrent.CompletableFuture

@DgsComponent
class UserDataFetcher {

    @DgsEntityFetcher(name = "Todo")
    fun Todo(values: Map<String?, Any?>): Todo {
        return Todo(values["id"] as String, null)
    }

    @DgsData(parentType = "Todo", field = "owner")
    fun ownerFetcher(dfe: DgsDataFetchingEnvironment): CompletableFuture<UserInfo>? {
        val dataLoader: DataLoader<TodoId, UserInfo> = dfe.getDataLoader(UserDataLoaderByTodoId::class.java)
        return dataLoader.load(TodoId(1))
    }
}