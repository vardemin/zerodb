package com.vardemin.zero.db.vault

import com.vardemin.zero.db.config.SerialZeroDbConfig
import com.vardemin.zero.db.exception.InvalidValueException
import com.vardemin.zero.db.exception.ZeroIOException
import com.vardemin.zero.db.source.ByteArraySource
import com.vardemin.zero.db.source.FileSource
import kotlinx.io.IOException
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.SerializationException
import kotlinx.serialization.StringFormat
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.encodeToString

class SerialZeroVault(
    private val name: String,
    private val config: SerialZeroDbConfig
) : ZeroVault {

    val serialFormat get() = config.serialFormat

    val source: ByteArraySource by lazy {
        FileSource(config.directory, name, "zeroc")
    }

    inline fun <reified T> read(): T {
        return try {
            if (serialFormat is BinaryFormat) {
                (serialFormat as BinaryFormat).decodeFromByteArray(source.readBytes())
            } else {
                (serialFormat as StringFormat).decodeFromString(source.readBytes().decodeToString())
            }
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

    inline fun <reified T> write(value: T) {
        try {
            if (serialFormat is BinaryFormat) {
                source.writeBytes((serialFormat as BinaryFormat).encodeToByteArray(value))
            } else {
                source.writeBytes((serialFormat as StringFormat).encodeToString(value).encodeToByteArray())
            }
        } catch (ex: IOException) {
            throw ZeroIOException(ex.message, ex.cause)
        } catch (ex: SerializationException) {
            throw InvalidValueException(ex)
        } catch (ex: IllegalStateException) {
            throw ZeroIOException(ex.message, ex.cause)
        }
    }
}