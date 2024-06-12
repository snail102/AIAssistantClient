package ru.anydevprojects.aiassistant.ui.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ru.anydevprojects.aiassistant.ui.theme.AIAssistantTheme

@Composable
fun DefaultButton(
    text: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    var size by remember { mutableStateOf(IntSize.Zero) }

    Button(
        modifier = modifier
            .defaultMinSize(minHeight = 48.dp),
        onClick = {
            if (!isLoading) {
                onClick()
            }
        },
        enabled = enabled
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                trackColor = Color.White
            )
        } else {
            Text(
                text = text
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDefaultButton() {
    AIAssistantTheme {
        Row {
            DefaultButton(
                text = "text",
                isLoading = true,
                onClick = {}
            )
            DefaultButton(
                text = "text",
                isLoading = false,
                onClick = {}
            )
        }
    }
}
