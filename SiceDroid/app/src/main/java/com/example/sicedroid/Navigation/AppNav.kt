package com.example.sicedroid.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sicedroid.ui.screens.Calificaciones
import com.example.sicedroid.ui.screens.CargaAcademica
import com.example.sicedroid.ui.screens.HomeScreen
import com.example.sicedroid.ui.screens.Kardex
import com.example.sicedroid.ui.screens.LoginScreen
import com.example.sicedroid.ui.screens.LoginViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)

    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route){
        composable(AppScreens.LoginScreen.route){
            LoginScreen(navController, loginViewModel)
        }
        composable(AppScreens.Kardex.route){
            Kardex(navController, loginViewModel)
        }
        composable(AppScreens.Calificaciones.route){
            Calificaciones(navController, loginViewModel)
        }
        composable(AppScreens.CargaAcademica.route){
            CargaAcademica(navController, loginViewModel)
        }
        composable(AppScreens.AccesoLoginApp.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
            type= NavType.StringType
        })){
            HomeScreen(navController, loginViewModel,it.arguments?.getString("text"))
        }
    }
}