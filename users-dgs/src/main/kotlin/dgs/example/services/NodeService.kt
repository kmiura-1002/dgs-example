package dgs.example.services

import com.netflix.dgs.codegen.generated.types.Node
import com.netflix.dgs.codegen.generated.types.UserInfo
import dgs.example.extensions.then
import dgs.example.value.UserInfoId
import org.springframework.stereotype.Service
import java.util.Random

interface INodeService {
    fun node(id: String): Node?
}

val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
fun randomStringByKotlinRandom(len: Int) = (1..len)
    .map { Random().nextInt(0, charPool.size).let { charPool[it] } }
    .joinToString("")

@Service
class NodeService : INodeService {
    override fun node(id: String): Node? {
        return id.contains("UserInfo").then(
            UserInfo(
                id = UserInfoId(Random().nextLong(100)).nodeId(),
                userName = randomStringByKotlinRandom(25),
                email = "${randomStringByKotlinRandom(10)}@example.com",
                firstName = randomStringByKotlinRandom(5),
                lastName = randomStringByKotlinRandom(5),
            )
        )
    }
}