package ru.anydevprojects.aiassistant.root.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.anydevprojects.aiassistant.feature.chat.presentation.ChatScreen
import ru.anydevprojects.aiassistant.feature.chat.presentation.ChatScreenNavigation
import ru.anydevprojects.aiassistant.ui.theme.AIAssistantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIAssistantTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ChatScreenNavigation
                ) {
                    composable<ChatScreenNavigation> {
                        ChatScreen()
                    }
                }
            }
        }
    }
}
