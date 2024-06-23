package ru.anydevprojects.aiassistant.ui.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import ru.anydevprojects.aiassistant.ui.theme.AIAssistantTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    buttonSize: ButtonSize = ButtonSize.DEFAULT,
    content: @Composable ButtonScope.() -> Unit
) {
    val padding = when (buttonSize) {
        ButtonSize.MIN -> PaddingValues(4.dp)
        ButtonSize.DEFAULT -> PaddingValues(8.dp)
        ButtonSize.MAX -> PaddingValues(16.dp)
    }

    val minHeight = when (buttonSize) {
        ButtonSize.MIN -> 24.dp
        ButtonSize.DEFAULT -> 48.dp
        ButtonSize.MAX -> 56.dp
    }

    val scope = remember(buttonSize) { ButtonScope(buttonSize) }

    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Button(
            modifier = modifier.defaultMinSize(
                minHeight = minHeight
            ),
            onClick = onClick,
            contentPadding = padding
        ) {
            with(scope) {
                if (isLoading) {
                    Progress()
                } else {
                    content()
                }
            }
        }
    }
}

@Preview
@Composable
private fun AssistantButtonPreview(
    @PreviewParameter(AssistantButtonConfigProvider::class) data: AssistantButtonData
) {
    AIAssistantTheme {
        AssistantButton(
            isLoading = data.isLoading,
            buttonSize = data.size,
            onClick = {}
        ) {
            Text(text = data.text)
        }
    }
}

private class AssistantButtonConfigProvider : PreviewParameterProvider<AssistantButtonData> {
    override val values: Sequence<AssistantButtonData> = sequenceOf (
        AssistantButtonData(
            text = "Button text min size",
            isLoading = false,
            size = ButtonSize.MIN
        ),
        AssistantButtonData(
            text = "Button text default size",
            isLoading = false,
            size = ButtonSize.DEFAULT
        ),
        AssistantButtonData(
            text = "Button text max size",
            isLoading = false,
            size = ButtonSize.MAX
        ),
        AssistantButtonData(
            text = "Button text max size",
            isLoading = true,
            size = ButtonSize.MAX
        )
    )
}

private data class AssistantButtonData(
    val text: String,
    val isLoading: Boolean,
    val size: ButtonSize
)
