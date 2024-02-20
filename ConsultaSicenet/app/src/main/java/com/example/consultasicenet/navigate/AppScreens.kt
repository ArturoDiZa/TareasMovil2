package com.example.consultasicenet.navigate

sealed class AppScreens(val route:String) {
    object Login:AppScreens("Login")
    object Info:AppScreens("Info")
}