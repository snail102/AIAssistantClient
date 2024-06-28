package ru.anydevprojects.aiassistant.core.database

import org.koin.dsl.module
import ru.anydevprojects.aiassistant.feature.chat.data.dao.ChatDao
import ru.anydevprojects.aiassistant.feature.chat.data.dao.MessageChatDao

val databaseModule = module {
    single<AssistantDatabase> { createDataBase(get()) }
    single<ChatDao> { provideChatDao(get()) }
    single<MessageChatDao> { provideMessageChatDao(get()) }
}
