package ru.anydevprojects.aiassistant.root.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.anydevprojects.aiassistant.root.domain.MainRepository
import ru.anydevprojects.aiassistant.root.presentation.models.MainState

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        checkTokens()
    }

    private fun checkTokens() {
        viewModelScope.launch {
            val hasToken = mainRepository.hasSavedTokens()

            _state.update {
                it.copy(
                    isLoading = false,
                    isAuthorized = hasToken
                )
            }
        }
    }
}
