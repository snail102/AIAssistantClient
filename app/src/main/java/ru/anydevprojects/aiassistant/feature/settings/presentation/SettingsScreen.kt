package ru.anydevprojects.aiassistant.feature.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.anydevprojects.aiassistant.feature.settings.presentation.models.SettingsIntent
import ru.anydevprojects.aiassistant.ui.components.button.DefaultButton
import ru.anydevprojects.aiassistant.ui.components.topAppBar.DefaultTopAppBar

@Composable
fun SettingsScreen(onBackClick: () -> Unit, viewModel: SettingsViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(
                title = "Настройки",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            DefaultButton(
                text = "Выйти из аккаунта",
                isLoading = state.isLogOutProcessing,
                onClick = {
                    viewModel.onIntent(SettingsIntent.OnLogOutBtnClick)
                }
            )
        }
    }
}
