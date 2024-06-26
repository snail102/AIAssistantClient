package ru.anydevprojects.aiassistant.root.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.anydevprojects.aiassistant.root.domain.MainRepository
import ru.anydevprojects.aiassistant.tokenStorage.TokenStorage

class MainRepositoryImpl(
    private val tokenStorage: TokenStorage
) : MainRepository {

    override fun isAuthorized(): Flow<Boolean> {
        return tokenStorage.getTokenFlow()
            .map { token ->
                Log.d("tokenStorage", token.toString())
                token.access.isNotEmpty() && token.refresh.isNotEmpty()
            }
    }
}
