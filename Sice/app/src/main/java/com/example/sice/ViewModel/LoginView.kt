package com.example.sice.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sice.AlumnosApplication
import com.example.sice.data.AlumnosRepository
import com.example.sice.modelos.Carga
import kotlinx.coroutines.async


class LoginView(private val alumnosRepository: AlumnosRepository):ViewModel(){
    var noControl by mutableStateOf("")
    var password by mutableStateOf("")
    var loginError by mutableStateOf(false)
    var expand by mutableStateOf(false)
    var userType by mutableStateOf("ALUMNO")


    fun updateExpandido(boolean: Boolean){
        expand=boolean
    }
    fun updateTipoUsuario(string: String){
        userType=string
    }

    fun updateMatricula(string: String){
        noControl=string
    }
    fun updatePassword(string: String){
        password=string
    }
    fun updateErrorLogin(boolean: Boolean){
        loginError=boolean
    }

    suspend fun getAccess(matricula: String, password: String, tipoUsuario:String): Boolean {
        return alumnosRepository.getAccess(matricula, password, tipoUsuario)
    }
    suspend fun getCarga():String{
        return alumnosRepository.getCarga()
    }

    suspend fun getInfo():String{
        val info = viewModelScope.async {
            alumnosRepository.getInfo()
        }
        return info.await()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlumnosApplication)
                val alumnosAplication = application.container.alumnosRepository
                LoginView(alumnosRepository = alumnosAplication)
            }
        }
    }
}

