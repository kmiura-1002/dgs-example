package dgs.example.value

data class TodoId(val id:Long): IdValueObject,NodeIdValueObject {
    override fun longValue(): Long = id

    override fun nodeId(): String = "Todo:$id"

}