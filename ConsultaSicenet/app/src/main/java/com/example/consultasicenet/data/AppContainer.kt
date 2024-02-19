package com.example.consultasicenet.data

import com.example.consultasicenet.network.SiceApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

val url = "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
        "  <soap:Body>\n" +
        "    <accesoLogin xmlns=\"http://tempuri.org/\">\n" +
        "      <strMatricula>string</strMatricula>\n" +
        "      <strContrasenia>string</strContrasenia>\n" +
        "      <tipoUsuario>ALUMNO or DOCENTE</tipoUsuario>\n" +
        "    </accesoLogin>\n" +
        "  </soap:Body>\n" +
        "</soap:Envelope>\n"

interface AppContainer {
    val SiceRepository: SiceRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "sicenet.surguanajuato.tecnm.mx"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: SiceApiService by lazy {
        retrofit.create(SiceApiService::class.java)
    }

    override val SiceRepository: SiceRepository by lazy {
        NetworkSiceRepository(retrofitService)
    }
}