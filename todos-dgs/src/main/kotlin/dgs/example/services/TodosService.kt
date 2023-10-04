package dgs.example.services

import com.netflix.dgs.codegen.generated.types.CreateTodoInput
import com.netflix.dgs.codegen.generated.types.Star
import com.netflix.dgs.codegen.generated.types.Todo
import com.netflix.dgs.codegen.generated.types.TodoStatus
import com.netflix.dgs.codegen.generated.types.UpdateTodoInput
import com.netflix.dgs.codegen.generated.types.UpdateTodoStatusInput
import dgs.example.exception.MyException
import dgs.example.value.TodoId
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.Random

interface ITodosService {
    fun todos(): List<Todo>

    fun addTodo(input: CreateTodoInput): Todo

    fun deleteTodo(id: TodoId): Todo

    fun updateTodo(input: UpdateTodoInput): Todo

    fun updateStatus(input: UpdateTodoStatusInput): Todo
}

val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
fun randomStringByKotlinRandom(len: Int) = (1..len)
    .map { Random().nextInt(0, charPool.size).let { charPool[it] } }
    .joinToString("")

@Service
class TodosService : ITodosService {
    override fun todos() =
        (1..10).map {
            Todo(
                id = TodoId(Random().nextLong(100)).nodeId(),
                title = randomStringByKotlinRandom(25),
                details = randomStringByKotlinRandom(255),
                status = TodoStatus.values().toList()[Random().nextInt(TodoStatus.values().size)],
                dueDate = null,
                updateAt = OffsetDateTime.now(),
                createAt = OffsetDateTime.now(),
                star = Star(1)
            )
        }

    override fun addTodo(input: CreateTodoInput): Todo =
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

    override fun deleteTodo(id: TodoId): Todo =
        throw MyException("throw Exception Example!")

    override fun updateTodo(input: UpdateTodoInput): Todo =
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

    override fun updateStatus(input: UpdateTodoStatusInput): Todo =
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

}
