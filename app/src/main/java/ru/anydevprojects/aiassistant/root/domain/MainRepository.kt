package ru.anydevprojects.aiassistant.root.domain

interface MainRepository {

    suspend fun hasSavedTokens(): Boolean
}
