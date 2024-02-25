package com.example.sice.modelos

data class InformacionAlumno (
    var reinscripcion:String="",
    var modEducativo:String="",
    var adeudo:String="",
    var foto:String="",
    var adeudoDescripcion:String="",
    var inscrito:Boolean=false,
    var estado:String="",
    var semestre:String="",
    var creditosAcum:Int=0,
    var creditosActu:Int=0,
    var especialidad:String="",
    var carrera:String="",
    var lineamiento:Int=0,
    var nombre:String="",
    var noControl:String="",
)