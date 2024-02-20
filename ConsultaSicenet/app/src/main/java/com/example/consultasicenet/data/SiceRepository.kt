package com.example.consultasicenet.data

import com.example.consultasicenet.model.Alumno
import com.example.consultasicenet.model.Credenciales
import com.example.consultasicenet.network.SiceApiService
import com.example.consultasicenet.network.SiceInfoService
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


interface SiceRepository {
    suspend fun getAlumno(matricula: String, password: String, tipoUsuario:String):Boolean
    suspend fun getInfo():String
}

class NetworkSiceRepository(
    private val SiceApiService: SiceApiService,
    private val SiceInfoService: SiceInfoService
) : SiceRepository {
    override suspend fun getAlumno(matricula: String, password: String, tipoUsuario:String):Boolean{
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
        SiceApiService.getCokies()
        return try {
            var respuestDatos=SiceApiService.getAcceso(requestBody).string().split("{","}")
            if(respuestDatos.size>1){
                val result = Gson().fromJson("{"+respuestDatos[1]+"}", Credenciales::class.java)
                result.acceso.equals("true")
            } else
                false
        }catch (e: IOException){
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
            val respuestaInfo=SiceInfoService.getInfo(requestBody).string().split("{","}")
            if(respuestaInfo.size>1){
                val result = Gson().fromJson("{"+respuestaInfo[1]+"}", Alumno::class.java)
                ""+result
            } else
                ""
        }catch (e: IOException){
            ""
        }
    }
}





