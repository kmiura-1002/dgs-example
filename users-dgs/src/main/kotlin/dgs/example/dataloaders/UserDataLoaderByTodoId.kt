package dgs.example.dataloaders

import com.netflix.dgs.codegen.generated.DgsConstants
import com.netflix.dgs.codegen.generated.types.UserInfo
import com.netflix.graphql.dgs.DgsDataLoader
import dgs.example.value.TodoId
import dgs.example.value.UserInfoId
import org.dataloader.MappedBatchLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = DgsConstants.TODO.Owner)
class UserDataLoaderByTodoId : MappedBatchLoader<TodoId, UserInfo> {
    val logger: Logger = LoggerFactory.getLogger(UserDataLoaderByTodoId::class.java)

    val users = mutableMapOf(
        TodoId(1) to UserInfo(
            id = UserInfoId(11).nodeId(),
            userName = "john smith",
            email = "test@example.com"
        ),
        TodoId(2) to UserInfo(
            id = UserInfoId(22).nodeId(),
            userName = "john smith 2",
            email = "john@example.com"
        )
    )

    override fun load(keys: MutableSet<TodoId>?): CompletionStage<MutableMap<TodoId, UserInfo>> {
        logger.info("User Data Load. keys=${keys}")
        return CompletableFuture.supplyAsync { users }
    }
}