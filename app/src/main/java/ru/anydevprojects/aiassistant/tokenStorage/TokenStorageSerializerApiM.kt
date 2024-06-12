package ru.anydevprojects.aiassistant.tokenStorage

import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import ru.anydevprojects.aiassistant.cryptoManager.CryptoManager
import ru.anydevprojects.aiassistant.domain.models.Token

class TokenStorageSerializerApiM(
    private val cryptoManager: CryptoManager
) : Serializer<Token> {

    override val defaultValue: Token
        get() = Token()

    override suspend fun readFrom(input: InputStream): Token {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = Token.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Token, output: OutputStream) {
        cryptoManager.encrypt(
            bytes = Json.encodeToString(
                serializer = Token.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }
}
