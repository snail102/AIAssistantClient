package ru.anydevprojects.aiassistant.root.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.anydevprojects.aiassistant.feature.authorization.presentation.AuthorizationScreen
import ru.anydevprojects.aiassistant.feature.authorization.presentation.AuthorizationScreenNavigation
import ru.anydevprojects.aiassistant.feature.chat.presentation.ChatScreen
import ru.anydevprojects.aiassistant.feature.chat.presentation.ChatScreenNavigation
import ru.anydevprojects.aiassistant.feature.registration.presentation.RegistrationScreen
import ru.anydevprojects.aiassistant.feature.registration.presentation.RegistrationScreenNavigation
import ru.anydevprojects.aiassistant.feature.settings.presentation.SettingsScreen
import ru.anydevprojects.aiassistant.feature.settings.presentation.SettingsScreenNavigation
import ru.anydevprojects.aiassistant.ui.theme.AIAssistantTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition { viewModel.state.value.isLoading }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIAssistantTheme {
                val state by viewModel.state.collectAsState()

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ChatScreenNavigation
                ) {
                    composable<ChatScreenNavigation> {
                        ChatScreen(
                            onSettingsClick = {
                            }
                        )
                    }

                    composable<AuthorizationScreenNavigation> {
                        AuthorizationScreen(
                            onRegistrationClick = {
                                navController.navigate(RegistrationScreenNavigation)
                            }
                        )
                    }

                    composable<RegistrationScreenNavigation> {
                        RegistrationScreen(
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable<SettingsScreenNavigation> {
                        SettingsScreen(
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
