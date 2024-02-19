package com.example.consultasicenet.network

import com.example.consultasicenet.model.Alumno
import retrofit2.http.GET

interface SiceApiService {
    @GET("photos")
    suspend fun getAlumno(): List<Alumno>
}

/*
interface MarsApiService {
    /**
     * Returns a [List] of [MarsPhoto] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}
 */
