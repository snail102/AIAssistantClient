package ru.anydevprojects.aiassistant.feature.registration.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import ru.anydevprojects.aiassistant.feature.authorization.presentation.models.AuthorizationIntent

@Composable
fun RegistrationScreen(
    onBackClick: () -> Unit,
    viewModel: RegistrationViewModel = koinViewModel()
) {
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
        }
    }
}
