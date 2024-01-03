package com.vardemin.zero.db.vault

import com.vardemin.zero.db.config.ZeroCborFileDbConfig
import com.vardemin.zero.db.exception.InvalidValueException
import com.vardemin.zero.db.exception.ZeroIOException
import com.vardemin.zero.db.source.ByteArraySource
import com.vardemin.zero.db.source.FileSource
import kotlinx.io.IOException
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray

class CborVault(
    private val name: String,
    private val config: ZeroCborFileDbConfig
) : ZeroVault {

    @OptIn(ExperimentalSerializationApi::class)
    val cbor get() = config.cbor

    val source: ByteArraySource by lazy {
        FileSource(config.directory, name, "zeroc")
    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> read(): T {
        return try {
            cbor.decodeFromByteArray(source.readBytes())
        } catch (ex: IOException) {
            throw ZeroIOException(ex.message, ex.cause)
        } catch (ex: SerializationException) {
            throw InvalidValueException(ex)
        } catch (ex: IllegalStateException) {
            throw ZeroIOException(ex.message, ex.cause)
        }
    }

    override fun clear() {
        source.writeBytes(ByteArray(0))
    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> write(value: T) {
        try {
            source.writeBytes(cbor.encodeToByteArray(value))
        } catch (ex: IOException) {
            throw ZeroIOException(ex.message, ex.cause)
        } catch (ex: SerializationException) {
            throw InvalidValueException(ex)
        } catch (ex: IllegalStateException) {
            throw ZeroIOException(ex.message, ex.cause)
        }
    }
}