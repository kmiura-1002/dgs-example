package dgs.example.services

import com.netflix.dgs.codegen.generated.types.Node
import com.netflix.dgs.codegen.generated.types.Star
import com.netflix.dgs.codegen.generated.types.Todo
import com.netflix.dgs.codegen.generated.types.TodoStatus
import dgs.example.extensions.then
import dgs.example.value.TodoId
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.Random

interface INodeService {
    fun node(id: String): Node?
}

@Service
class NodeService : INodeService {
    override fun node(id: String): Node? {
        return id.contains("Todo").then(
            Todo(
                id = TodoId(Random().nextLong(100)).nodeId(),
                title = randomStringByKotlinRandom(25),
                details = randomStringByKotlinRandom(255),
                status = TodoStatus.values().toList()[Random().nextInt(TodoStatus.values().size)],
                dueDate = null,
                updateAt = OffsetDateTime.now(),
                createAt = OffsetDateTime.now(),
                star = Star(0)
            )
        )
    }
}