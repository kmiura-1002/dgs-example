package dgs.example.datafetchers

import com.netflix.dgs.codegen.generated.types.Node
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import dgs.example.services.INodeService

@DgsComponent
class NodeDataFetcher(private val nodeService: INodeService) {

    @DgsQuery
    fun node(id: String): Node? {
        return nodeService.node(id)
    }
}