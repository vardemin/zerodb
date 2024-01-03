package com.vardemin.zero.db

import kotlinx.serialization.Serializable

@Serializable
data class ExampleEntity(
    val id: Int = 0,
    val name: String = "name"
)