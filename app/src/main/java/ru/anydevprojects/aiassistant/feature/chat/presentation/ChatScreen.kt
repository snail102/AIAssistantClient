package ru.anydevprojects.aiassistant.feature.chat.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.anydevprojects.aiassistant.feature.chat.presentation.models.ChatIntent
import ru.anydevprojects.aiassistant.feature.chat.presentation.models.ChatMessageUi

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(onSettingsClick: () -> Unit, viewModel: ChatViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    DismissibleNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            DismissibleDrawerSheet(
                drawerContainerColor = Color.LightGray
            ) {
                NavigationDrawerItem(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    label = { Text(text = "Label 1") },
                    selected = false,
                    onClick = { }
                )
            }
        },
        content = {
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
                                onClick = onSettingsClick
                            ) {
                                Icon(
                                    Icons.Default.Settings,
                                    contentDescription = "Settings"
                                )
                            }
                        }
                    )
                }
            ) {
//        ModalBottomSheet(
//            modifier = Modifier,
//            onDismissRequest = {
//            }
//        ) {

                Column(
                    modifier = Modifier.padding(it)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(
                            items = state.messages,
                            key = { message ->
                                message.content
                            }
                        ) { chatMessageUi ->
                            MessageItem(
                                isUser = chatMessageUi is ChatMessageUi.UserChatMessage,
                                content = chatMessageUi.content
                            )
                        }
                    }

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

            //   }
        }
    )
}

@Composable
private fun MessageItem(isUser: Boolean, content: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ) {
        val textAlign = if (isUser) TextAlign.End else TextAlign.Start
        Text(
            modifier = Modifier.padding(16.dp),
            text = content,
            textAlign = textAlign
        )
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
private fun PreviewBottomInputControl() {
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
