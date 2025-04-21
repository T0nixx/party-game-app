package com.party.wear.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.party.wear.connection.GameConnectionViewModel
import kotlinx.coroutines.delay

@Composable
fun GameStartScreen(
    navController: NavController
) {
    val connectionViewModel: GameConnectionViewModel = hiltViewModel()
    var roomCode by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Room Code 입력",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = roomCode,
            onValueChange = { if (it.all { c -> c.isDigit() }) roomCode = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f),
            textStyle = LocalTextStyle.current.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                connectionViewModel.connect(
                    serverUrl = "ws://192.168.0.11:8080/ws/session/$roomCode",
                    roomCode = roomCode,
                    userId = null,
                )
            },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text("입장")
        }
    }

    val messages = connectionViewModel.messageFlow.collectAsState(initial = null)
    messages.value?.let { msg ->
        if (msg.type == "gameStart") {
            val payload = msg.payload as? Map<*, *> ?: return
            val game = payload["game"]?.toString()
            if (game == "rps") {
                LaunchedEffect(Unit) {
                    delay(300)
                    navController.navigate("rps")
                }
            }
        }
    }
}
