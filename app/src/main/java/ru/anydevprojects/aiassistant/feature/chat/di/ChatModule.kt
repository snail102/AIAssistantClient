package ru.anydevprojects.aiassistant.feature.chat.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.anydevprojects.aiassistant.feature.chat.data.ChatRepositoryImpl
import ru.anydevprojects.aiassistant.feature.chat.domain.ChatRepository
import ru.anydevprojects.aiassistant.feature.chat.presentation.ChatViewModel

val chatModule = module {
    factory<ChatRepository> { ChatRepositoryImpl(get()) }
    viewModel { ChatViewModel(get()) }
}
