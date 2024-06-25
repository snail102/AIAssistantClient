package ru.anydevprojects.aiassistant.core.database

import org.koin.dsl.module
import ru.anydevprojects.aiassistant.feature.chat.data.dao.ChatDao

val databaseModule = module {
    single<AssistantDatabase> { createDataBase(get()) }
    single<ChatDao> { provideDao(get()) }
}
