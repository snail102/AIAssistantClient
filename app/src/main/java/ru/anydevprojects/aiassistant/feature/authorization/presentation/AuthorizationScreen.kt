package ru.anydevprojects.aiassistant.feature.authorization.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import ru.anydevprojects.aiassistant.feature.authorization.presentation.models.AuthorizationIntent
import ru.anydevprojects.aiassistant.ui.components.button.DefaultButton

@Composable
fun AuthorizationScreen(
    onRegistrationClick: () -> Unit,
    viewModel: AuthorizationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = state.login,
                onValueChange = {
                    viewModel.onIntent(AuthorizationIntent.OnChangeLogin(it))
                }
            )

            OutlinedTextField(
                value = state.password,
                onValueChange = {
                    viewModel.onIntent(AuthorizationIntent.OnChangePassword(it))
                }
            )

            DefaultButton(
                text = "Авторизоваться",
                onClick = {
                    viewModel.onIntent(AuthorizationIntent.OnAuthClick)
                }
            )

            DefaultButton(
                text = "Зарегистрироваться",
                onClick = onRegistrationClick
            )
        }
    }
}
