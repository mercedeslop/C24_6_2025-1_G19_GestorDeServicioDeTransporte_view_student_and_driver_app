package com.jennyfer.sapallanay.view_student_app.data.remote

import com.jennyfer.sapallanay.view_student_app.data.model.Ruta
import retrofit2.http.GET

interface RutaApiService {
    @GET("rutas")
    suspend fun getRutas(): List<Ruta>

}