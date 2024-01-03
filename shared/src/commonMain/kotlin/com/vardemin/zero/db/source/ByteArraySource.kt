package com.vardemin.zero.db.source

interface ByteArraySource {
    fun readBytes(): ByteArray
    fun writeBytes(array: ByteArray)
}