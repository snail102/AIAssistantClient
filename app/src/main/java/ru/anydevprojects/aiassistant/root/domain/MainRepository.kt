package ru.anydevprojects.aiassistant.root.domain

import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun isAuthorized(): Flow<Boolean>
}
