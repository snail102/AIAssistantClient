package ru.anydevprojects.aiassistant.tokenStorage

import androidx.datastore.core.Serializer
import io.ktor.utils.io.charsets.Charset
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import ru.anydevprojects.aiassistant.domain.models.Token

class TokenStorageSerializer() : Serializer<Token> {
    override val defaultValue: Token
        get() = Token()

    override suspend fun readFrom(input: InputStream): Token {
        return try {
            val tokenStringJson = input.readTextAndClose()
            Json.decodeFromString(
                deserializer = Token.serializer(),
                string = tokenStringJson
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Token, output: OutputStream) {
        val tokenStringJson = Json.encodeToString(
            serializer = Token.serializer(),
            value = t
        )
        tokenStringJson.writeToOutputStreamWriter(outputStream = output)
    }
}

private fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8): String {
    return this.reader(charset).use { it.readText() }
}

private fun String.writeToOutputStreamWriter(
    outputStream: OutputStream,
    charset: Charset = Charsets.UTF_8
) {
    OutputStreamWriter(outputStream, charset).use { writer ->
        writer.write(this)
        writer.flush()
    }
}
