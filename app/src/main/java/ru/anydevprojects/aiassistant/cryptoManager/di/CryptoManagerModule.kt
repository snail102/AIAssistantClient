package ru.anydevprojects.aiassistant.cryptoManager.di

import android.annotation.SuppressLint
import org.koin.dsl.module
import ru.anydevprojects.aiassistant.cryptoManager.CryptoManager
import ru.anydevprojects.aiassistant.cryptoManager.CryptoManagerImpl

@SuppressLint("NewApi")
val cryptoManagerModule = module {
    single<CryptoManager> { CryptoManagerImpl() }
}
