package dgs.example.exception

import com.netflix.graphql.dgs.exceptions.DgsException
import com.netflix.graphql.types.errors.ErrorType

open class MyException(message: String = "", cause: Exception? = null) :
    DgsException(message, cause, ErrorType.BAD_REQUEST)