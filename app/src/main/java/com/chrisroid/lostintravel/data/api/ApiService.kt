package com.chrisroid.lostintravel.data.api

import com.chrisroid.lostintravel.domain.model.Destination
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TravelApiService {
    @GET("recommendedPlaces")
    suspend fun getRecommendedPlaces(): List<Destination>
}

object ApiClient {
    private const val BASE_URL = "https://lostapi.frontendlabs.co.uk/"

    val instance: TravelApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
            .create(TravelApiService::class.java)
    }
}