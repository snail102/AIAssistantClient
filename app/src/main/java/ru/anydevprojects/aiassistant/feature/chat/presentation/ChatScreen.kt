package ru.anydevprojects.aiassistant.feature.chat.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.anydevprojects.aiassistant.feature.chat.presentation.models.ChatIntent
import ru.anydevprojects.aiassistant.feature.chat.presentation.models.ChatMessageUi

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize().imePadding()
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
        //   }
    }
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
        modifier = modifier.fillMaxWidth().padding(16.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = message,
            onValueChange = onMessageChange
        )

        IconButton(
            modifier = Modifier.size(24.dp),
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
