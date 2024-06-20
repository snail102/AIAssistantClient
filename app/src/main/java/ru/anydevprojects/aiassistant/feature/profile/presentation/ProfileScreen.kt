package ru.anydevprojects.aiassistant.feature.profile.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.anydevprojects.aiassistant.ui.components.button.DefaultButton
import ru.anydevprojects.aiassistant.ui.components.topAppBar.DefaultTopAppBar

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    settingsToScreen: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(title = "Профиль", onBackClick = onBackClick)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            if (state.isLoadingUserGptTokens) {
                CircularProgressIndicator()
            }
            state.gptTokenStatistics?.let {
                GptTokenStatisticsCard(
                    modifier = Modifier.fillMaxWidth(),
                    availableGtpTokens = it.availableGptTokens,
                    usedGptTokens = it.usedGptTokens
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                onClick = settingsToScreen,
                text = "Настройки"
            )
        }
    }
}

@Composable
private fun GptTokenStatisticsCard(
    availableGtpTokens: Int,
    usedGptTokens: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier.padding(16.dp)
        ) {
            Text(
                text = "Доступно токенов: $availableGtpTokens"
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Использовано токенов: $usedGptTokens"
            )
        }
    }
}
