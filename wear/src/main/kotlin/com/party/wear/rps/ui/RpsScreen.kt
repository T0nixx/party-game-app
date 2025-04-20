package com.party.wear.rps.ui

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Text
import com.party.shared.rps.model.RpsChoice
import com.party.wear.rps.viewmodel.RpsViewModel

@Composable
@Preview
fun RpsScreen(viewModel: RpsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var showResultDialog by remember { mutableStateOf(false) }

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
                        onClick = { viewModel.onChoice(choice) },
                        shape = RoundedCornerShape(percent = 50),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
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
                CircularProgressIndicator(indicatorColor = Color.White)
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
                    Text(
                        text = "상대: ${uiState.result!!.winningChoice.name}",
                        color = Color.White
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = { showResultDialog = false }) {
                        Text("확인")
                    }
                }
            }
        }
    }
}