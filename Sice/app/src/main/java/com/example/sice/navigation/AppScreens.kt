package com.example.sice.navigation

sealed class AppScreens(val route:String) {
    object Login:AppScreens("Login")
    object Info:AppScreens("Info")
}