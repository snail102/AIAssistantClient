package ru.anydevprojects.aiassistant.tokenStorage

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.first
import ru.anydevprojects.aiassistant.cryptoManager.CryptoManager
import ru.anydevprojects.aiassistant.domain.models.Token

private const val NAME_DATASTORE_PREFERENCE = "token-storage.json"

class TokenStorageImpl(
    private val application: Application,
    private val cryptoManager: CryptoManager
) : TokenStorage {

    private val Context.dataStore by dataStore(
        fileName = NAME_DATASTORE_PREFERENCE,
        serializer = getTokenStorageSerializer()
    )

    private fun getTokenStorageSerializer(): Serializer<Token> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TokenStorageSerializerApiM(cryptoManager)
        } else {
            TokenStorageSerializer()
        }
    }

    override suspend fun saveToken(token: Token) {
        application.applicationContext.dataStore.updateData {
            token
        }
    }

    override suspend fun getToken(): Token {
        return application.applicationContext.dataStore.data.first()
    }
}
