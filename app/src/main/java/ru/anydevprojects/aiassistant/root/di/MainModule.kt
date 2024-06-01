package ru.anydevprojects.aiassistant.root.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.anydevprojects.aiassistant.root.data.MainRepositoryImpl
import ru.anydevprojects.aiassistant.root.domain.MainRepository
import ru.anydevprojects.aiassistant.root.presentation.MainViewModel

val mainModule = module {
    viewModel { MainViewModel(get()) }
    single<MainRepository> { MainRepositoryImpl(get()) }
}
