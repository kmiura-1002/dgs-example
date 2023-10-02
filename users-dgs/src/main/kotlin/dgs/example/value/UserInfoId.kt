package dgs.example.value

data class UserInfoId(val id:Long): IdValueObject,NodeIdValueObject {
    override fun longValue(): Long = id

    override fun nodeId(): String = "UserInfo:$id"

}