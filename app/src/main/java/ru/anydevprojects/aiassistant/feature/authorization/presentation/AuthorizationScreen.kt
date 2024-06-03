package ru.anydevprojects.aiassistant.feature.authorization.presentation

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthorizationScreen(
    onRegistrationClick: () -> Unit,
    viewModel: AuthorizationViewModel = koinViewModel()
) {
}
