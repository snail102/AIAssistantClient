package ru.anydevprojects.aiassistant.feature.chat.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.anydevprojects.aiassistant.feature.chat.presentation.models.ChatIntent
import ru.anydevprojects.aiassistant.feature.chat.presentation.models.MessageUi
import ru.anydevprojects.aiassistant.ui.theme.AIAssistantTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(profileToScreen: () -> Unit, viewModel: ChatViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                if (state.isLoadingChatAll) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn {
                        items(
                            items = state.chatHistory,
                            key = {
                                it.chatId
                            }
                        ) { chatHistory ->
                            NavigationDrawerItem(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                label = { Text(text = chatHistory.firstMessagePreview) },
                                selected = false,
                                onClick = {
                                    viewModel.onIntent(
                                        ChatIntent.OnChatHistoryClick(chatId = chatHistory.chatId)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isOpen) {
                                        drawerState.close()
                                    } else {
                                        drawerState.open()
                                    }
                                }
                            }
                        ) {
                            if (drawerState.isClosed) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Open all dialogs"
                                )
                            } else {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Close all dialogs"
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = profileToScreen
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Profile"
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(it)
            ) {
                if (state.isLoadingCurrentChatMessages) {
                    Box(
                        modifier = Modifier,
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    ChatMessages(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        messages = state.messages
                    )

                    if (state.errorMessage.isNotEmpty()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            text = state.errorMessage
                        )
                    }

                    BottomInputControl(
                        modifier = Modifier.fillMaxWidth(),
                        isAvailableSendBtn = state.enabledSendBtn,
                        message = state.inputtedMessage,
                        onMessageChange = {
                            viewModel.onIntent(ChatIntent.OnChangeMessage(it))
                        },
                        onClickSendBtn = {
                            viewModel.onIntent(ChatIntent.SendMessage)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatMessages(messages: List<MessageUi>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = messages,
            key = { message ->
                message.content
            }
        ) { chatMessageUi ->
            MessageItem(
                modifier = Modifier.fillMaxWidth(),
                isUser = chatMessageUi is MessageUi.UserMessage,
                content = chatMessageUi.content
            )
        }
    }
}

@Composable
private fun MessageItem(isUser: Boolean, content: String, modifier: Modifier = Modifier) {
    val bottomStartRounded = if (isUser) 16.dp else 0.dp
    val bottomEndRounded = if (isUser) 0.dp else 16.dp
    val contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
    val paddingStart = if (isUser) 42.dp else 8.dp
    val paddingEnd = if (isUser) 8.dp else 42.dp
    val backgroundColor = if (isUser) Color.Cyan else Color.LightGray

    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        Card(
            modifier = Modifier.padding(start = paddingStart, end = paddingEnd),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = bottomStartRounded,
                bottomEnd = bottomEndRounded
            ),
            colors = CardDefaults.cardColors().copy(containerColor = backgroundColor)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = content
            )
        }
    }
}

@Composable
private fun BottomInputControl(
    isAvailableSendBtn: Boolean,
    message: String,
    onMessageChange: (String) -> Unit,
    onClickSendBtn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = message,
            onValueChange = onMessageChange
        )

        Spacer(
            modifier = Modifier.width(16.dp)
        )

        IconButton(
            modifier = Modifier
                .size(24.dp),
            enabled = isAvailableSendBtn,
            onClick = onClickSendBtn
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "send"
            )
        }
    }
}

@Preview
@Composable
private fun ChatMessagesPreview() {
    AIAssistantTheme {
        ChatMessages(
            modifier = Modifier.fillMaxSize(),
            messages = listOf(
                MessageUi.AssistantMessage(
                    id = 0,
                    content = "content from assistant"
                ),
                MessageUi.UserMessage(
                    id = 1,
                    content = "user answer"
                ),
                MessageUi.AssistantMessage(
                    id = 2,
                    content = "answer from assistant"
                )

            )
        )
    }
}

@Preview
@Composable
private fun BottomInputControlPreview() {
    Box(
        modifier = Modifier.background(color = Color.White)
    ) {
        BottomInputControl(
            isAvailableSendBtn = true,
            message = "133333",
            onMessageChange = {
            },
            onClickSendBtn = {
            }
        )
    }
}
