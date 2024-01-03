package com.vardemin.zero.db

import com.vardemin.zero.db.config.ZeroCborFileDbConfig
import kotlinx.io.files.Path
import kotlinx.io.files.SystemTemporaryDirectory
import org.junit.Test
import kotlin.test.assertEquals

class ZeroDbTest {

    @Test
    fun normalTest() {
        val directory = Path(SystemTemporaryDirectory, "zero")

        val zeroDb = ZeroDb(
            ZeroCborFileDbConfig(
                directory
            )
        )
        val entity = ExampleEntity()
        zeroDb.set("random", entity)
        val actual = zeroDb.get<ExampleEntity>("random")
        assertEquals(entity, actual)
    }
}