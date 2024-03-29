package com.example.consultasicenet.model

data class Alumno( val id: String,
                   var fechaReins:String="",
                   var modEducativo:String="",
                   var adeudo:String="",
                   var urlFoto:String="",
                   var adeudoDescripcion:String="",
                   var inscrito:Boolean=false,
                   var estatus:String="",
                   var semActual:String="",
                   var cdtosAcumulados:Int=0,
                   var cdtosActuales:Int=0,
                   var especialidad:String="",
                   var carrera:String="",
                   var lineamiento:Int=0,
                   var nombre:String="",
                   var matricula:String="",
)

