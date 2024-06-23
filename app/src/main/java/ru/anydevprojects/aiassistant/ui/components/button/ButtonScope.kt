package ru.anydevprojects.aiassistant.ui.components.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Stable
class ButtonScope(private val buttonSize: ButtonSize) {

    @Composable
    fun Text(text: String) {
        val style = when (buttonSize) {
            ButtonSize.MIN -> typography.bodySmall
            ButtonSize.DEFAULT -> typography.bodyMedium
            ButtonSize.MAX -> typography.bodyLarge
        }
        androidx.compose.material3.Text(
            text = text,
            style = style
        )
    }

    @Composable
    fun Progress() {
        val size = when (buttonSize) {
            ButtonSize.MIN -> 16.dp
            ButtonSize.DEFAULT -> 32.dp
            ButtonSize.MAX -> 48.dp
        }
        CircularProgressIndicator(modifier = Modifier.size(size))
    }
}
