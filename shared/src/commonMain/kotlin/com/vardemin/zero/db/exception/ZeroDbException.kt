package com.vardemin.zero.db.exception

sealed class ZeroDbException(message: String?, cause: Throwable?): Throwable(message, cause)

class InvalidValueException(cause: Throwable?): ZeroDbException("Invalid value format", cause)
class ZeroIOException(message: String?, cause: Throwable?): ZeroDbException(message, cause)