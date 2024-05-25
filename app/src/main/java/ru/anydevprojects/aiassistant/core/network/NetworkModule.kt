package ru.anydevprojects.aiassistant.core.network

import org.koin.dsl.module

val networkModule = module {
    single {
        getNetworkClient()
    }
}
