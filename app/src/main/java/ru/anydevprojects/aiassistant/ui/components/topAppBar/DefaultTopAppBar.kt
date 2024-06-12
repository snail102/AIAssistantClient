package ru.anydevprojects.aiassistant.ui.components.topAppBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(title: String, onBackClick: () -> Unit = {}, enabledBackBtn: Boolean = true) {
    TopAppBar(
        title = {
            Text(title)
        },
        navigationIcon = {
            if (enabledBackBtn) {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}
