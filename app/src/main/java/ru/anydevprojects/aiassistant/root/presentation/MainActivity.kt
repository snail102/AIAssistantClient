package ru.anydevprojects.aiassistant.root.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.anydevprojects.aiassistant.core.ui.SIDE_EFFECTS_KEY
import ru.anydevprojects.aiassistant.feature.authorization.presentation.AuthorizationScreen
import ru.anydevprojects.aiassistant.feature.authorization.presentation.AuthorizationScreenNavigation
import ru.anydevprojects.aiassistant.feature.chat.presentation.ChatScreen
import ru.anydevprojects.aiassistant.feature.chat.presentation.ChatScreenNavigation
import ru.anydevprojects.aiassistant.feature.registration.presentation.ConfirmEmailScreen
import ru.anydevprojects.aiassistant.feature.registration.presentation.ConfirmEmailScreenNavigation
import ru.anydevprojects.aiassistant.feature.registration.presentation.RegistrationScreen
import ru.anydevprojects.aiassistant.feature.registration.presentation.RegistrationScreenNavigation
import ru.anydevprojects.aiassistant.feature.settings.presentation.SettingsScreen
import ru.anydevprojects.aiassistant.feature.settings.presentation.SettingsScreenNavigation
import ru.anydevprojects.aiassistant.root.presentation.models.MainEvent
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

                LaunchedEffect(SIDE_EFFECTS_KEY) {
                    viewModel.event.collect { event ->
                        when (event) {
                            MainEvent.NavigateToAuthorization -> navController.navigate(
                                AuthorizationScreenNavigation,
                                navOptions = navOptions {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                            )

                            MainEvent.NavigateToChat -> navController.navigate(
                                ChatScreenNavigation,
                                navOptions = navOptions {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                            )
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = ChatScreenNavigation
                ) {
                    composable<ChatScreenNavigation> {
                        ChatScreen(
                            onSettingsClick = {
                                navController.navigate(SettingsScreenNavigation)
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
                            },
                            confirmEmailToScreen = { login ->
                                navController.navigate(
                                    ConfirmEmailScreenNavigation(login = login)
                                )
                            }
                        )
                    }

                    composable<ConfirmEmailScreenNavigation> {
                        val args = it.toRoute<ConfirmEmailScreenNavigation>()
                        ConfirmEmailScreen(
                            login = args.login,
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
