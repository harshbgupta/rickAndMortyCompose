package com.hell.rickandmorty.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hell.rickandmorty.ui.detail.CharacterDetail
import com.hell.rickandmorty.ui.home.Home
import com.hell.rickandmorty.ui.splash.SplashScreen
import com.hell.rickandmorty.util.Screen

@Composable
fun RickAndMortyNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Screen.Home.route
        } else {
            Screen.Splash.route
        }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            Home(
                navigateToCharacterDetail = { characterId ->
                    navController.navigate(Screen.CharacterDetail.route + "/$characterId")
                }
            )
        }
        composable(Screen.CharacterDetail.route + "/{characterId}") {
            it.arguments?.getString("characterId")?.let { characterId ->
                CharacterDetail(
                    characterId = characterId,
                    navigateToBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}