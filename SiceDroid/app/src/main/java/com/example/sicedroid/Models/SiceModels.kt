package com.example.sicedroid.Models

data class Login (
    var acceso:String,
    var estatus:String,
    var contrasenia:String,
    var matricula:String
)

data class InfoAlumno (
    var nombre:String="",
    var fechaReins:String="",
    var semActual:String="",
    var cdtosAcumulados:Int=0,
    var cdtosActuales:Int=0,
    var carrera:String="",
    var matricula:String="",
    var especialidad:String="",
)


data class Materia(
    var ClvMat: String,
    var ClvOfiMat: String,
    var Materia: String="",
    var Cdts:String="",
    var Calif:String="",
    var Acred:String="",
    var Ordinario:String="",
    var S1:String="",
    var P1:String="",
    var A1:String="",
    var S2:String="",
    var P2:String="",
    var A2:String="",
)

data class carga_academica(
    var Docente: String="",
    var clvOficial: String="",
    var Sabado: String="",
    var Viernes: String="",
    var Jueves: String="",
    var Miercoles: String="",
    var Martes: String="",
    var Lunes: String="",
    var EstadoMateria: String="",
    var CreditosMateria: String="",
    var Materia: String="",
    var Grupo: String="",
)

data class final(
    var materia: String="",
    var grupo: String="",
    var calif: String="",
)

data class Cal_Unidad(
    var C13: String = "",
    var C12: String = "",
    var C11: String = "",
    var C10: String = "",
    var C9: String = "",
    var C8: String = "",
    var C7: String = "",
    var C6: String = "",
    var C5: String = "",
    var C4: String = "",
    var C3: String = "",
    var C2: String = "",
    var C1: String = "",
    var Materia: String = "",
    var Grupo: String = "",
)