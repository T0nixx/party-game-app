// ✅ RpsScreen.kt
package com.party.wear.rps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.party.shared.rps.model.RpsChoice
import com.party.shared.rps.model.RpsResult
import com.party.wear.connection.GameConnectionViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RpsScreen() {
    val rpsViewModel: RpsViewModel = hiltViewModel()
    val connectionViewModel: GameConnectionViewModel = hiltViewModel()
    val uiState by rpsViewModel.uiState.collectAsState()
    var showResultDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        connectionViewModel.messageFlow.collectLatest { message ->
            if (message.type == "result") {
                val map = message.payload as? Map<*, *> ?: return@collectLatest
                val result = RpsResult(
                    userChoice = RpsChoice.valueOf(map["userChoice"].toString()),
                    isWinner = map["isWinner"] as Boolean
                )
                rpsViewModel.setResult(result)
            }
        }
    }

    LaunchedEffect(uiState.result, uiState.isLoading) {
        showResultDialog = uiState.result != null && !uiState.isLoading
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "가위바위보",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RpsChoice.entries.forEach { choice ->
                    Button(
                        onClick = {
                            rpsViewModel.onChoice(choice)
                            connectionViewModel.send("submit", mapOf("choice" to choice.name))
                        },
                        shape = RoundedCornerShape(percent = 50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(48.dp)
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = choice.name,
                            color = Color.White
                        )
                    }
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Spacer(modifier = Modifier.height(48.dp))
            }
        }

        if (showResultDialog && uiState.result != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xAA000000)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.DarkGray, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (uiState.result!!.isWinner) "🎉 승리!" else "😢 패배",
                        color = if (uiState.result!!.isWinner) Color.Green else Color.Red,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { showResultDialog = false }) {
                        Text("확인")
                    }
                }
            }
        }
    }
}
