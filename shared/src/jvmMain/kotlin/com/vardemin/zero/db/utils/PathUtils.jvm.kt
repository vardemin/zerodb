package com.vardemin.zero.db.utils

import kotlinx.io.files.Path
import java.io.File

actual fun Path.createFileIfNotExists() {
    val file = File(toString())
    file.parentFile?.let {
        if (!it.exists()) {
            it.mkdirs()
        }
    }
    if (!file.exists()) {
        file.createNewFile()
    }
}