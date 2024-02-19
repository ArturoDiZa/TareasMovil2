package com.example.consultasicenet.data

import com.example.consultasicenet.model.Alumno
import com.example.consultasicenet.network.SiceApiService


interface SiceRepository {
    suspend fun getAlumno(): List<Alumno>
}

class NetworkSiceRepository(
    private val SiceApiService: SiceApiService
) : SiceRepository {
    override suspend fun getAlumno(): List<Alumno> = SiceApiService.getAlumno()
}
