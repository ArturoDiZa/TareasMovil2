package com.example.sicedroid.Navigation


    sealed class AppScreens(val route: String){
    object LoginScreen: AppScreens("login_screen")
    object AccesoLoginApp: AppScreens("acceso_login_app")

    object Kardex: AppScreens("cardex")

    object CargaAcademica: AppScreens("carga_academica")

    object Calificaciones: AppScreens("calificaciones")
}