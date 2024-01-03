package com.vardemin.zero.db.source

import co.touchlab.stately.concurrency.Synchronizable
import co.touchlab.stately.concurrency.synchronize
import com.vardemin.zero.db.utils.createFileIfNotExists
import kotlinx.io.Buffer
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray

internal class FileSource(
    directory: Path,
    filename: String,
    extension: String
) : ByteArraySource, Synchronizable() {

    private val file: Path by lazy {
        val dbName = "$filename.$extension"
        Path(directory, dbName).also {
            it.createFileIfNotExists()
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun readBytes(): ByteArray {
        return synchronize {
            SystemFileSystem.source(file).use {
                it.buffered().readByteArray()
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun writeBytes(array: ByteArray) {
        return synchronize {
            val buffer = Buffer().apply {
                write(array)
            }
            SystemFileSystem.sink(file).use {
                it.write(buffer, buffer.size)
            }
        }
    }

}