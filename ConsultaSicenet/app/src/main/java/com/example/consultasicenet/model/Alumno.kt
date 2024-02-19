package com.example.consultasicenet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Alumno( val id: String,
                   @SerialName(value = "img_src")
                   val imgSrc: String
)

