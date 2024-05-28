package ru.anydevprojects.aiassistant.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val access: String = "",
    val refresh: String = ""
)
