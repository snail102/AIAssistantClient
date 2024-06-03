package ru.anydevprojects.aiassistant.feature.registration.presentation

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    onBackClick: () -> Unit,
    viewModel: RegistrationViewModel = koinViewModel()
) {
}
