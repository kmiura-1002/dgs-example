package dgs.example.extensions.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline val Any.logger: Logger
    get() = LoggerFactory.getLogger(this.javaClass)