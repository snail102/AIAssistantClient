package ru.anydevprojects.aiassistant.feature.registration.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ktor.http.parameters
import io.ktor.http.parametersOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.anydevprojects.aiassistant.core.ui.CollectSideEffect
import ru.anydevprojects.aiassistant.feature.registration.presentation.models.ConfirmEmailEvent
import ru.anydevprojects.aiassistant.feature.registration.presentation.models.ConfirmEmailIntent
import ru.anydevprojects.aiassistant.ui.animation.ShakingState
import ru.anydevprojects.aiassistant.ui.animation.rememberShakingState
import ru.anydevprojects.aiassistant.ui.animation.shakable
import ru.anydevprojects.aiassistant.ui.components.button.DefaultButton
import ru.anydevprojects.aiassistant.ui.components.topAppBar.DefaultTopAppBar
import ru.anydevprojects.aiassistant.ui.theme.AIAssistantTheme

@Composable
fun ConfirmEmailScreen(
    onBackClick: () -> Unit,
    login: String,
    viewModel: ConfirmEmailViewModel = koinViewModel(
        parameters = {
            parametersOf(login)
        }
    )
) {
    val state by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val shakeState = rememberShakingState(
        strength = ShakingState.Strength.Strong,
        direction = ShakingState.Direction.LEFT_THEN_RIGHT
    )

    CollectSideEffect(viewModel.event) { event ->
        when (event) {
            ConfirmEmailEvent.FailedToResendCode -> scope.launch {
                snackbarHostState.showSnackbar("Не удалось отправить повторно код подтверждения")
            }

            ConfirmEmailEvent.IncorrectCode -> {
                scope.launch { shakeState.shake() }
            }

            ConfirmEmailEvent.SuccessToResendCode -> scope.launch {
                snackbarHostState.showSnackbar("Код подтверждения повторно отправлен")
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .padding(WindowInsets.ime.asPaddingValues()),
                hostState = snackbarHostState
            )
        },
        topBar = {
            DefaultTopAppBar(
                title = "Подтверждение почты",
                onBackClick = onBackClick,
                enabledBackBtn = true
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CodeInput(
                modifier = Modifier.shakable(shakeState),
                text = state.code,
                enabled = !(state.isCheckCorrectCode || state.isProcessingSendRetryCode),
                isLoading = state.isCheckCorrectCode,
                shouldShowCursor = true,
                shouldCursorBlink = true,
                onValueChange = { code, isFull ->
                    viewModel.onIntent(ConfirmEmailIntent.OnChangeCode(code))
                }
            )
            Spacer(modifier = Modifier.height(64.dp))
            DefaultButton(
                text = "Отправить повторно на почту код",
                enabled = !(state.isProcessingSendRetryCode || state.isCheckCorrectCode),
                isLoading = state.isProcessingSendRetryCode,
                onClick = {
                    viewModel.onIntent(ConfirmEmailIntent.OnRetrySendMailBtnClick)
                }
            )
        }
    }
}

@Composable
private fun CodeInput(
    text: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    length: Int = 4,
    enabled: Boolean = true,
    shouldShowCursor: Boolean = false,
    shouldCursorBlink: Boolean = false,
    onValueChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (text.length > length) {
            throw IllegalArgumentException("OTP should be $length digits")
        }
    }
    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(text, selection = TextRange(text.length)),
        enabled = enabled,
        onValueChange = {
            if (it.text.length <= length) {
                onValueChange.invoke(it.text, it.text.length == length)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(length) { index ->
                    CharacterContainer(
                        index = index,
                        text = text,
                        shouldShowCursor = shouldShowCursor,
                        shouldCursorBlink = shouldCursorBlink,
                        isLoading = isLoading
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
internal fun CharacterContainer(
    index: Int,
    text: String,
    shouldShowCursor: Boolean,
    shouldCursorBlink: Boolean,
    isLoading: Boolean
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xFF60DDAD),
        targetValue = Color(0xFF4285F4),
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "color"
    )

    val isFocused = text.length == index
    val character = when {
        index < text.length -> text[index].toString()
        else -> ""
    }

    // Cursor visibility state
    val cursorVisible = remember { mutableStateOf(shouldShowCursor) }

    // Blinking effect for the cursor
    LaunchedEffect(key1 = isFocused) {
        if (isFocused && shouldShowCursor && shouldCursorBlink) {
            while (true) {
                delay(800) // Adjust the blinking speed here
                cursorVisible.value = !cursorVisible.value
            }
        }
    }

    Box(contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier
                .width(36.dp)
                .border(
                    width = when {
                        isFocused -> 2.dp
                        else -> 1.dp
                    },
                    color = when {
                        isFocused -> Color.Red
                        isLoading -> animatedColor
                        else -> Color.Black
                    },
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(2.dp),
            text = character,
            style = MaterialTheme.typography.headlineLarge,
            color = if (isFocused) Color.Black else Color.Red,
            textAlign = TextAlign.Center
        )

        // Display cursor when focused
        AnimatedVisibility(visible = isFocused && cursorVisible.value) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(2.dp)
                    .height(24.dp) // Adjust height according to your design
                    .background(Color.Red)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCodeInput() {
    AIAssistantTheme {
        CodeInput(
            text = "",
            onValueChange = { value, isFull ->
            }
        )
    }
}
