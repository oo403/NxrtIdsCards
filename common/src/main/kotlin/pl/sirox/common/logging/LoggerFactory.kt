package pl.sirox.common.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LoggerFactory {

    fun get(clazz: Class<*>) : Logger {
        return LoggerFactory.getLogger(clazz)
    }

    fun get(name: String) : Logger {
        return LoggerFactory.getLogger(name)
    }

}