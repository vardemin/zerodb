package com.vardemin.zero.db.config

import kotlinx.io.files.Path
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.modules.SerializersModule

sealed interface ZeroDbConfig

data class ZeroCborFileDbConfig(
    val directory: Path,
    val ignoreUnknownKeys: Boolean = true,
    val encodeDefaults: Boolean = true,
    val serializersModule: SerializersModule? = null
) : ZeroDbConfig {
    @OptIn(ExperimentalSerializationApi::class)
    val cbor by lazy {
        Cbor {
            ignoreUnknownKeys = this@ZeroCborFileDbConfig.ignoreUnknownKeys
            encodeDefaults = this@ZeroCborFileDbConfig.encodeDefaults
            serializersModule = this@ZeroCborFileDbConfig.serializersModule ?: serializersModule
        }
    }
}