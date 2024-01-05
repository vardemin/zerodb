package com.vardemin.zero.db.config

import kotlinx.io.files.Path
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.StringFormat

sealed interface ZeroDbConfig

class SerialZeroDbConfig(
    val directory: Path,
    val serialFormat: SerialFormat
) : ZeroDbConfig {
    init {
        require(serialFormat is BinaryFormat || serialFormat is StringFormat) {
            "SerialFormat must be BinaryFormat or StringFormat"
        }
    }
}