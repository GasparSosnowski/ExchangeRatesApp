package com.example.schema.data.service

import com.example.schema.data.modelsDto.RecentExchangeRatesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FixerApi {

    @GET("latest")
    suspend fun getRecentExchangeRates(@Query("access_key") api_key : String) : RecentExchangeRatesDto

    @GET("{date}")
    suspend fun getNextDaysData(@Path("date") date: String, @Query("access_key") api_key : String) : RecentExchangeRatesDto
}