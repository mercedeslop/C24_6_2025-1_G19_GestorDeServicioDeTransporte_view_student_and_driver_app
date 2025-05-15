package com.jennyfer.sapallanay.view_student_app.data.repository

import com.jennyfer.sapallanay.view_student_app.data.remote.RetrofitInstance

class RutaRepository {
    suspend fun getRutas() = RetrofitInstance.api.getRutas()
}