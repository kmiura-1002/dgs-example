package dgs.example.services

import com.netflix.dgs.codegen.generated.types.PageInfo
import com.netflix.dgs.codegen.generated.types.Todo
import com.netflix.dgs.codegen.generated.types.TodoConnection
import com.netflix.dgs.codegen.generated.types.TodoEdges
import com.netflix.dgs.codegen.generated.types.TodoStatus
import dgs.example.value.TodoId
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.Random

interface ITodosService {
    fun todos(): TodoConnection
}
val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
fun randomStringByKotlinRandom(len: Int) = (1..len)
    .map { Random().nextInt(0, charPool.size).let { charPool[it] } }
    .joinToString("")

@Service
class TodosService : ITodosService {
    override fun todos() =
        TodoConnection(
            (1..10).map {
                TodoEdges(
                    Todo(
                        id = TodoId(Random().nextLong(100)).nodeId(),
                        title = randomStringByKotlinRandom(25),
                        details = randomStringByKotlinRandom(255),
                        status = TodoStatus.values().toList()[Random().nextInt(TodoStatus.values().size)],
                        dueDate = null,
                        updateAt = OffsetDateTime.now(),
                        createAt = OffsetDateTime.now(),
                    ),
                    (TodoId(Random().nextLong(100)).nodeId())
                )
            },
            PageInfo(
                TodoId(Random().nextLong(100)).nodeId(),
                TodoId(Random().nextLong(100)).nodeId(),
                false,
                false
            )
        )
}