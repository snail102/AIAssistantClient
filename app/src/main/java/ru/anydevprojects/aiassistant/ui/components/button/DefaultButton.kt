package ru.anydevprojects.aiassistant.ui.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DefaultButton(
    text: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {
            if (!isLoading) {
                onClick()
            }
        }
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                text = text
            )
        }
    }
}
