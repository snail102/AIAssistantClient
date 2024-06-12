package ru.anydevprojects.aiassistant.feature.registration.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.anydevprojects.aiassistant.core.ui.CollectSideEffect
import ru.anydevprojects.aiassistant.feature.registration.presentation.models.RegistrationEvent
import ru.anydevprojects.aiassistant.feature.registration.presentation.models.RegistrationIntent
import ru.anydevprojects.aiassistant.ui.components.button.DefaultButton
import ru.anydevprojects.aiassistant.ui.components.topAppBar.DefaultTopAppBar

@Composable
fun RegistrationScreen(
    onBackClick: () -> Unit,
    confirmEmailToScreen: (String) -> Unit,
    viewModel: RegistrationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    CollectSideEffect(viewModel.event) { event ->
        when (event) {
            is RegistrationEvent.NavigateToConfirmationEmail -> {
                confirmEmailToScreen(event.login)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(
                title = "Регистрация",
                onBackClick = onBackClick,
                enabledBackBtn = true
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.login,
                label = {
                    Text("Логин")
                },
                onValueChange = {
                    viewModel.onIntent(RegistrationIntent.OnChangeLogin(it))
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
                    viewModel.onIntent(RegistrationIntent.OnChangePassword(it))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.confirmPassword,
                label = {
                    Text("Повторный пароль")
                },
                onValueChange = {
                    viewModel.onIntent(RegistrationIntent.OnChangeConfirmPassword(it))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.email,
                label = {
                    Text("Электронная почта")
                },
                onValueChange = {
                    viewModel.onIntent(RegistrationIntent.OnChangeEmail(it))
                }
            )

            Spacer(modifier = Modifier.height(48.dp))

            DefaultButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Зарегистрироваться",
                isLoading = state.isLoading,
                enabled = state.enabledRegisterBtn,
                onClick = {
                    viewModel.onIntent(RegistrationIntent.OnRegistrationBtnClick)
                }
            )
        }
    }
}
