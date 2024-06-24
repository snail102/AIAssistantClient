package ru.anydevprojects.aiassistant.root.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import ru.anydevprojects.aiassistant.root.domain.MainRepository
import ru.anydevprojects.aiassistant.root.presentation.models.MainEvent
import ru.anydevprojects.aiassistant.root.presentation.models.MainState

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState(isLoading = true))
    val state = _state.asStateFlow()

    private val _event: Channel<MainEvent> = Channel()
    val event = _event.receiveAsFlow()

    private var isAuthorized: Boolean? = null

    init {
        checkTokens()
    }

    private fun checkTokens() {
        mainRepository.isAuthorized()
            .flowOn(Dispatchers.IO)
            .onEach { isAuthorized ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isAuthorized = isAuthorized
                    )
                }

                if (this.isAuthorized != isAuthorized) {
                    if (!isAuthorized) {
                        _event.send(MainEvent.NavigateToAuthorization)
                    } else {
                        _event.send(MainEvent.NavigateToChat)
                    }
                }
            }.launchIn(viewModelScope)
    }
}
