package com.example.sice.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sice.CargaAcademica
import com.example.sice.dataStudent
import com.example.sice.loginApp

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.Login.route,
    ){
        composable(route =AppScreens.Login.route){
            loginApp(navController)
        }
        composable(route =AppScreens.Info.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type= NavType.StringType
            })){
            dataStudent(navController,it.arguments?.getString("text"))
        }
        composable(route =AppScreens.CargaA.route+"{text}"){
            CargaAcademica(navController,it.arguments?.getString("text"))
        }

    }
}