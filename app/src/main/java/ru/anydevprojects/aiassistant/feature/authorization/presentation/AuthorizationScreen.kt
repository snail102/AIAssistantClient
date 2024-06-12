package ru.anydevprojects.aiassistant.feature.authorization.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.anydevprojects.aiassistant.core.ui.CollectSideEffect
import ru.anydevprojects.aiassistant.feature.authorization.presentation.models.AuthorizationEvent
import ru.anydevprojects.aiassistant.feature.authorization.presentation.models.AuthorizationIntent
import ru.anydevprojects.aiassistant.ui.components.button.DefaultButton

@Composable
fun AuthorizationScreen(
    onRegistrationClick: () -> Unit,
    viewModel: AuthorizationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    CollectSideEffect(viewModel.event) { event ->
        when (event) {
            is AuthorizationEvent.ShowErrorMessage -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .padding(WindowInsets.ime.asPaddingValues()),
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .imePadding()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.login,
                    label = {
                        Text("Логин")
                    },
                    onValueChange = {
                        viewModel.onIntent(AuthorizationIntent.OnChangeLogin(it))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.password,
                    label = {
                        Text("Пароль")
                    },
                    onValueChange = {
                        viewModel.onIntent(AuthorizationIntent.OnChangePassword(it))
                    }
                )

                Spacer(modifier = Modifier.height(48.dp))

                DefaultButton(
                    modifier = Modifier,
                    text = "Авторизоваться",
                    isLoading = state.isLoading,
                    enabled = state.enabledAuthBtn,
                    onClick = {
                        viewModel.onIntent(AuthorizationIntent.OnAuthClick)
                    }
                )
            }
            DefaultButton(
                modifier = Modifier,
                text = "Зарегистрироваться",
                enabled = !state.isLoading,
                onClick = onRegistrationClick
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
