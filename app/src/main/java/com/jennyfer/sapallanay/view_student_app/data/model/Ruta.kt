package com.jennyfer.sapallanay.view_student_app.data.model

import java.io.Serializable

data class Ruta(
    val tituloRuta: String,
    val numeroDisponibilidad: String,
    val turno: String,
    val horaSalida: String,
    val horaLlegada: String,
    val unidad: String,
    val conductor: String
) : Serializable