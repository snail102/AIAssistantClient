package ru.anydevprojects.aiassistant.tokenStorage

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.anydevprojects.aiassistant.cryptoManager.CryptoManager
import ru.anydevprojects.aiassistant.domain.models.Token

private const val NAME_DATASTORE_PREFERENCE = "token-storage.json"

class TokenStorageImpl(
    private val application: Application,
    private val cryptoManager: CryptoManager
) : TokenStorage {

    private var _accessToken: String = ""
    private var _refreshToken: String = ""

    override val accessToken: String
        get() = _accessToken

    override val refreshToken: String
        get() = _refreshToken

    override fun getTokenFlow(): Flow<Token> {
        return application.applicationContext.dataStore.data.map {
            Log.d("Tokens", "access: ${it.access} refresh: ${it.refresh}")
            _accessToken = it.access
            _refreshToken = it.refresh
            it
        }
    }

    private val Context.dataStore by dataStore(
        fileName = NAME_DATASTORE_PREFERENCE,
        serializer = getTokenStorageSerializer()
    )

    private fun getTokenStorageSerializer(): Serializer<Token> {
        return TokenStorageSerializer()
    }

    override suspend fun initToken(): Token {
        val token = getToken()
        _accessToken = token.access
        _refreshToken = token.refresh
        return token
    }

    override suspend fun saveToken(token: Token) {
        withContext(Dispatchers.IO) {
            _accessToken = token.access
            _refreshToken = token.refresh
            application.applicationContext.dataStore.updateData {
                token
            }
        }
    }

    override suspend fun getToken(): Token {
        return withContext(Dispatchers.IO) {
            application.applicationContext.dataStore.data.first()
        }
    }

    override suspend fun removeTokens() {
        withContext(Dispatchers.IO) {
            application.applicationContext.dataStore.updateData {
                Token(access = "", refresh = "")
            }
        }
    }
}
