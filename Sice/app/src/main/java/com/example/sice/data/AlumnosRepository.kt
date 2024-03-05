package com.example.sice.data

import android.util.Log
import com.example.sice.modelos.AlumnoCred
import com.example.sice.modelos.Carga
import com.example.sice.modelos.GenericModel
import com.example.sice.modelos.InformacionAlumno
import com.example.sice.network.AlumnoCarAcad
import com.example.sice.network.ApiSicenet
import com.example.sice.network.AlumnoInfo
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


interface AlumnosRepository {
    suspend fun getAccess(noControl: String, password: String, userType:String):Boolean
    suspend fun getInfo():String
    suspend fun getCarga():String
}

class NetworkAlumnosRepository(
    private val apiSic: ApiSicenet,
    private val aluInf: AlumnoInfo,
    private val aluCar: AlumnoCarAcad
): AlumnosRepository {
    override suspend fun getAccess(noControl: String, password: String, userType: String): Boolean {
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <accesoLogin xmlns="http://tempuri.org/">
                  <strMatricula>${noControl}</strMatricula>
                  <strContrasenia>${password}</strContrasenia>
                  <tipoUsuario>${userType}</tipoUsuario>
                </accesoLogin>
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody = xml.toRequestBody()
        apiSic.getCokies()
        return try {
            var respuesta = apiSic.getAcceso(requestBody).string().split("{", "}")
            if (respuesta.size > 1) {
                val result = Gson().fromJson("{" + respuesta[1] + "}", AlumnoCred::class.java)
                result.acceso.equals("true")
            } else false
        } catch (e: IOException) {
            false
        }
    }

    override suspend fun getInfo(): String {
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody = xml.toRequestBody()
        return try {
            val respuestaInfo = aluInf.getInfo(requestBody).string().split("{", "}")
            if (respuestaInfo.size > 1) {
                val result =
                    Gson().fromJson("{" + respuestaInfo[1] + "}", InformacionAlumno::class.java)
                "" + result
            } else ""
        } catch (e: IOException) {
            ""
        }
    }


    override suspend fun getCarga(): String {
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getCargaAcademicaByAlumno xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody = xml.toRequestBody()
        try {
            val respuestaCarga = aluCar.getCarga(requestBody).string().split("{", "}")

            if (respuestaCarga.size > 1) {
                val cargaMaterias = mutableListOf<Carga>()
                for (elem in respuestaCarga) {
                    if (elem.contains("clvOficial")) {
                        val result = Gson().fromJson("{" + elem + "}", Carga::class.java)
                        Log.d("Elemento Carga", result.toString())
                        cargaMaterias.add(result)
                    }
                }
                return "" + cargaMaterias
            } else
                return ""
        } catch (e: IOException) {
            return ""
        }
    }
}





