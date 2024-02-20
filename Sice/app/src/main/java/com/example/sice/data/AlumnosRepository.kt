package com.example.sice.data

import com.example.sice.modelos.CredencialesAlumno
import com.example.sice.modelos.InformacionAlumno
import com.example.sice.network.AlumnoApiService
import com.example.sice.network.AlumnoInfoService
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream
import java.io.IOException


interface AlumnosRepository {
    suspend fun getAccess(matricula: String, password: String, tipoUsuario:String):Boolean
    suspend fun getInfo():String
}

class NetworkAlumnosRepository(
    private val alumnoApiService: AlumnoApiService,
    private val alumnoInfoService: AlumnoInfoService
): AlumnosRepository{
    override suspend fun getAccess(matricula: String, password: String, tipoUsuario:String):Boolean{
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <accesoLogin xmlns="http://tempuri.org/">
                  <strMatricula>${matricula}</strMatricula>
                  <strContrasenia>${password}</strContrasenia>
                  <tipoUsuario>${tipoUsuario}</tipoUsuario>
                </accesoLogin>
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        alumnoApiService.getCokies()
        return try {
            var respuestDatos=alumnoApiService.getAcceso(requestBody).string().split("{","}")
            if(respuestDatos.size>1){
                val result = Gson().fromJson("{"+respuestDatos[1]+"}", CredencialesAlumno::class.java)
                result.acceso.equals("true")
            } else
                false
        }catch (e:IOException){
            false
        }
    }

    override suspend fun getInfo():String{
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        return try {
            val respuestaInfo=alumnoInfoService.getInfo(requestBody).string().split("{","}")
            if(respuestaInfo.size>1){
                val result = Gson().fromJson("{"+respuestaInfo[1]+"}", InformacionAlumno::class.java)
                ""+result
            } else
                ""
        }catch (e:IOException){
            ""
        }
    }
}





