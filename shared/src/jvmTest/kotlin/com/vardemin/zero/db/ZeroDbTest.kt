package com.vardemin.zero.db

import com.vardemin.zero.db.config.SerialZeroDbConfig
import kotlinx.io.files.Path
import kotlinx.io.files.SystemTemporaryDirectory
import kotlinx.serialization.cbor.Cbor
import org.junit.Test
import kotlin.test.assertEquals

class ZeroDbTest {

    @Test
    fun normalTest() {
        val directory = Path(SystemTemporaryDirectory, "zero")

        val zeroDb = ZeroDb(
            SerialZeroDbConfig(
                directory,
                Cbor {
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                }
            )
        )
        val entity = ExampleEntity()
        zeroDb.set("random", entity)
        val actual = zeroDb.get<ExampleEntity>("random")
        assertEquals(entity, actual)
    }
}