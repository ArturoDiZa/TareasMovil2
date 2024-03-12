package com.example.sicedroid.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sicedroid.Models.Cal_Unidad
import com.example.sicedroid.Models.InfoAlumno
import com.example.sicedroid.Models.Login
import com.example.sicedroid.Models.final
import com.example.sicedroid.Models.Materia
import com.example.sicedroid.Models.carga_academica
import com.example.sicedroid.Data.Repository
import com.example.sicedroid.Data.SiceContainer
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.awaitResponse


class LoginViewModel(private val Repository: Repository) : ViewModel() {

    suspend fun getAccess(noControl: String, contrasenia: String): Boolean {
        return Repository.getAccess(noControl, contrasenia)
    }

    suspend fun getInfo():String{
        val informacion = viewModelScope.async {
            Repository.obtenerInfo()
        }
        return informacion.await()
    }

    suspend fun getKardex():List<Materia>{
        //private val workManager = WorkManager.getInstance(context)
        val informacion =
        viewModelScope.async {
            Repository.obtenerCardex()
        }
        return informacion.await()
    }

    suspend fun getCargaAcademica():List<carga_academica>{
        //private val workManager = WorkManager.getInstance(context)
        val informacion =
            viewModelScope.async {
                Repository.obtenerCargaAcademica()
            }
        return informacion.await()
    }

    suspend fun getCalificacionFinal():List<final>{
        //private val workManager = WorkManager.getInstance(context)
        val informacion =
            viewModelScope.async {
                Repository.obtenerCalificacionFinal()
            }
        return informacion.await()
    }

    suspend fun getCalificacionUnidad():List<Cal_Unidad>{
        //private val workManager = WorkManager.getInstance(context)
        val informacion =
            viewModelScope.async {
                Repository.obtenerCalificacionUnidad()
            }
        return informacion.await()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SiceContainer)
                val alumnosAplication = application.container.alumnosRepository
                LoginViewModel(Repository = alumnosAplication)
            }
        }
    }
}