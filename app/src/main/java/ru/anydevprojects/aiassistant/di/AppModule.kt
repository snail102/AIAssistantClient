package ru.anydevprojects.aiassistant.di

import org.koin.dsl.module
import ru.anydevprojects.aiassistant.core.network.networkModule
import ru.anydevprojects.aiassistant.feature.chat.di.chatModule

val appModule = module {
    includes(
        networkModule,
        chatModule
    )
}
