package com.vardemin.zero.db

import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import org.junit.Test
import kotlin.test.assertEquals

class CborTest {
    val cbor = Cbor {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    @Test
    fun stringTest() {
        val bao = cbor.encodeToByteArray<String>("blablabla")
        assert(bao.isNotEmpty())
        val str: String = cbor.decodeFromByteArray<String>(bao)
        assert(str == "blablabla")
    }

    @Test
    fun intTest() {
        val bao = cbor.encodeToByteArray<Int>(231)
        assert(bao.isNotEmpty())
        val value = cbor.decodeFromByteArray<Int>(bao)
        assert(value == 231)
    }

    @Test
    fun floatTest() {
        val bao = cbor.encodeToByteArray<Float>(231.32f)
        assert(bao.isNotEmpty())
        val value = cbor.decodeFromByteArray<Float>(bao)
        assert(value == 231.32f)
    }

    @Test
    fun longTest() {
        val bao = cbor.encodeToByteArray<Long>(231L)
        assert(bao.isNotEmpty())
        val value = cbor.decodeFromByteArray<Long>(bao)
        assert(value == 231L)
    }

    @Test
    fun booleanTest() {
        val bao = cbor.encodeToByteArray<Boolean>(true)
        assert(bao.isNotEmpty())
        val value = cbor.decodeFromByteArray<Boolean>(bao)
        assert(value)
    }

    fun entityTest() {
        val entity = ExampleEntity(123, "randomname")
        val bao = cbor.encodeToByteArray<ExampleEntity>(entity)
        assert(bao.isNotEmpty())
        val value = cbor.decodeFromByteArray<ExampleEntity>(bao)
        assertEquals(entity, value)
    }
}