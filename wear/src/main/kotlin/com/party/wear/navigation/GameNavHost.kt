package com.party.wear.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.party.wear.rps.RpsScreen
import com.party.wear.start.GameStartScreen

@Composable
fun GameNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "start") {
        composable("start") {
            GameStartScreen(navController)
        }
        composable("rps") {
            RpsScreen()
        }
    }
}
