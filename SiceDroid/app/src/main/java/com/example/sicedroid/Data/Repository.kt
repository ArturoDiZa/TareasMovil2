package com.example.sicedroid.Data

import android.util.Log
import androidx.compose.ui.graphics.vector.VectorProperty
import com.example.sicedroid.Models.Cal_Unidad
import com.example.sicedroid.Models.InfoAlumno
import com.example.sicedroid.Models.Login
import com.example.sicedroid.Models.final
import com.example.sicedroid.Models.Materia
import com.example.sicedroid.Models.carga_academica
import com.example.sicedroid.Network.SiceApiService
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
interface Repository {
    suspend fun getAccess(noControl: String, contrasenia: String):Boolean
    suspend fun obtenerInfo():String
    suspend fun obtenerCardex():List<Materia>
    suspend fun obtenerCargaAcademica(): List<carga_academica>
    suspend fun obtenerCalificacionFinal(): List<final>
    suspend fun obtenerCalificacionUnidad(): List<Cal_Unidad>
}

class NetworkAlumnosRepository(
    private val ApiService: SiceApiService,
): Repository {

    override suspend fun getAccess(noControl: String, contrasenia: String ):Boolean{
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <accesoLogin xmlns="http://tempuri.org/">
                  <strMatricula>${noControl}</strMatricula>
                  <strContrasenia>${contrasenia}</strContrasenia>
                  <tipoUsuario>ALUMNO</tipoUsuario>
                </accesoLogin>
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        ApiService.obtenerCookies()

        try {
            var respuesta=ApiService
                .obtenerAcceso(requestBody)
                .string().split("{","}")
            if(respuesta.size>1){
                val result = Gson().fromJson("{"+respuesta[1]+"}",
                    Login::class.java)
                return result.acceso.equals("true")
            } else
                return false
        }catch (e: IOException){
            return false
        }
    }

    override suspend fun obtenerInfo():String{
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        try {
            val respuesta=ApiService.getInfo(requestBody).
            string().split("{","}")
            if(respuesta.size>1){
                val result = Gson().fromJson("{"+respuesta[1]+"}",
                    InfoAlumno::class.java)
                return ""+result
            } else
                return ""
        }catch (e: IOException){
            return ""
        }
    }

    override suspend fun obtenerCardex():List<Materia>{
        val xml="""
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAllKardexConPromedioByAlumno xmlns="http://tempuri.org/">
                  <aluLineamiento>1</aluLineamiento>
                </getAllKardexConPromedioByAlumno>
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()
        val  requestBody=xml.toRequestBody()
        try{
            val respuesta = ApiService.getKardex(requestBody).string().split("{","}")
            var List : MutableList<Materia> = mutableListOf()
            if(respuesta.size>1){
                for (parte in respuesta){
                    //Log.d("KARDEX-$parte",parte)
                    if(parte.contains("\"ClvMat\"")){
                        List.add(Gson().fromJson("{"+parte+"}", Materia::class.java))
                    }
                }
                //var result = Gson().fromJson("{"+respuesta[2]+"}", Materia::class.java)
                //return ""
            }else{
                return emptyList()
            }
            return List
        }
        catch(e: IOException) {
            Log.d("ERROR",e.toString())
            return emptyList()
        }
    }

    override suspend fun obtenerCargaAcademica(): List<carga_academica> {
        val xml="""
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getCargaAcademicaByAlumno xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()
        val  requestBody=xml.toRequestBody()
        try{
            val respuesta = ApiService.getCargaAcademica(requestBody).string().split("{","}")
            var List : MutableList<carga_academica> = mutableListOf()
            if(respuesta.size>1){
                for (parte in respuesta){
                    //Log.d("KARDEX-$parte",parte)
                    if(parte.contains("\"clvOficial\"")){
                        List.add(Gson().fromJson("{"+parte+"}", carga_academica::class.java))
                    }
                }
                //var result = Gson().fromJson("{"+respuesta[2]+"}", Materia::class.java)
                //return ""
            }else{
                return emptyList()
            }
            return List
        }
        catch(e: IOException) {
            Log.d("ERROR",e.toString())
            return emptyList()
        }
    }

    override suspend fun obtenerCalificacionFinal(): List<final> {
        val xml="""
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAllCalifFinalByAlumnos xmlns="http://tempuri.org/">
                  <bytModEducativo>2</bytModEducativo>
                </getAllCalifFinalByAlumnos>
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()
        val  requestBody=xml.toRequestBody()
        try{
            val respuesta = ApiService.getCalificacionesFinales(requestBody).string().split("{","}")
            var List : MutableList<final> = mutableListOf()
            if(respuesta.size>1){
                for (parte in respuesta){
                    //Log.d("KARDEX-$parte",parte)
                    if(parte.contains("\"grupo\"")){
                        List.add(Gson().fromJson("{"+parte+"}", final::class.java))
                    }
                }
                //var result = Gson().fromJson("{"+respuesta[2]+"}", Materia::class.java)
                //return ""
            }else{
                return emptyList()
            }
            return List
        }
        catch(e: IOException) {
            Log.d("ERROR",e.toString())
            return emptyList()
        }
    }

    override suspend fun obtenerCalificacionUnidad(): List<Cal_Unidad> {
        val xml="""
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getCalifUnidadesByAlumno xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()
        val  requestBody=xml.toRequestBody()
        try{
            val respuesta = ApiService.getCalificacionesUnidades(requestBody).string().split("{","}")
            var List : MutableList<Cal_Unidad> = mutableListOf()
            if(respuesta.size>1){
                for (parte in respuesta){
                    //Log.d("KARDEX-$parte",parte)
                    if(parte.contains("\"Grupo\"")){
                        List.add(Gson().fromJson("{"+parte+"}", Cal_Unidad::class.java))
                    }
                }
                //var result = Gson().fromJson("{"+respuesta[2]+"}", Materia::class.java)
                //return ""
            }else{
                Log.d("FALLOOOO","")
                return emptyList()
            }
            return List
        }
        catch(e: IOException) {
            Log.d("ERROR",e.toString())
            return emptyList()
        }
    }


}