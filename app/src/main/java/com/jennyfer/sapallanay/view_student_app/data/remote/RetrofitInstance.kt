package com.jennyfer.sapallanay.view_student_app.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://682406a365ba058033989ccd.mockapi.io/api/v1/"

    val api: RutaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RutaApiService::class.java)
    }
}