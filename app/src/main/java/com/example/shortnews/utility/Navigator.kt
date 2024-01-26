package com.example.shortnews.utility

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shortnews.pages.HomePage
import com.example.shortnews.pages.HomePageActivity

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomePage()
        }
        // Add more destinations as needed
    }
}
