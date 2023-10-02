package dgs.example.extensions

infix fun <T> Boolean.then(other: T) = if (this) other else null
