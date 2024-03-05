package com.example.sice.network

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiSicenet {
    @Headers(
        "Content-Type: text/xml",
        "SOAPAction: \"http://tempuri.org/accesoLogin\"",
        )

    @POST("ws/wsalumnos.asmx")
    suspend fun getAcceso(
        @Body requestBody: RequestBody
    ): ResponseBody

    @GET("ws/wsalumnos.asmx")
    suspend fun getCokies(): ResponseBody


    //Traer la kardex
    @Headers(
        "Content-Type: text/xml",
        "SOAPAction: \"http://tempuri.org/getAllKardexConPromedioByAlumno\""
    )
    @POST("ws/wsalumnos.asmx")
    suspend fun getKardex(
        @Body requestBody: RequestBody
    ): ResponseBody

    //Traer la carga academica
    @Headers(
        "Content-Type: text/xml",
        "SOAPAction: \"http://tempuri.org/getCargaAcademicaByAlumno\""
    )
    @POST("ws/wsalumnos.asmx")
    suspend fun getCargaAcademica(
        @Body requestBody: RequestBody
    ): ResponseBody

    //Traer calificaciones finales
    @Headers(
        "Content-Type: text/xml",
        "SOAPAction: \"http://tempuri.org/getAllCalifFinalByAlumnos\""
    )
    @POST("ws/wsalumnos.asmx")
    suspend fun getCalificacionesFinales(
        @Body requestBody: RequestBody
    ): ResponseBody

    //Traer calificaciones parciales
    @Headers(
        "Content-Type: text/xml",
        "SOAPAction: \"http://tempuri.org/getCalifUnidadesByAlumno\""
    )
    @POST("ws/wsalumnos.asmx")
    suspend fun getCalificacionesUnidades(
        @Body requestBody: RequestBody
    ): ResponseBody
}
