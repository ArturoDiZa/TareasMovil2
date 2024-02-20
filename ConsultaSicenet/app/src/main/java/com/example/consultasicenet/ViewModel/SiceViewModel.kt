package com.example.consultasicenet.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.consultasicenet.SiceAplication
import com.example.consultasicenet.data.SiceRepository
import kotlinx.coroutines.async

class SiceViewModel(private val SiceRepository: SiceRepository): ViewModel() {
    var matricula by mutableStateOf("")
    var password by mutableStateOf("")
    var errorLogin by mutableStateOf(false)
    var expandido by mutableStateOf(false)
    var tipoUsuario by mutableStateOf("ALUMNO")

    fun updateExpandido(boolean: Boolean){
        expandido=boolean
    }
    fun updateTipoUsuario(string: String){
        tipoUsuario=string
    }

    fun updateMatricula(string: String){
        matricula=string
    }
    fun updatePassword(string: String){
        password=string
    }
    fun updateErrorLogin(boolean: Boolean){
        errorLogin=boolean
    }

    suspend fun getAccess(matricula: String, password: String, tipoUsuario:String): Boolean {
        return SiceRepository.getAlumno(matricula, password, tipoUsuario)
    }

    suspend fun getInfo():String{
        val informacion = viewModelScope.async {
            SiceRepository.getInfo()
        }
        return informacion.await()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SiceAplication)
                val SiceAplication = application.container.SiceRepository
                SiceViewModel(SiceRepository = SiceAplication)
            }
        }
    }
}