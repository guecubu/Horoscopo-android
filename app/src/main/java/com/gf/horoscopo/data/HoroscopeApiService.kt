package com.gf.horoscopo.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface HoroscopeApiService {
    @GET("api/v1/get-horoscope/daily")
    suspend fun getDailyHoroscope(@Query("sign") sign: String): HoroscopeResponse

    @GET("api/v1/get-horoscope/weekly")
    suspend fun getWeeklyHoroscope(@Query("sign") sign: String): HoroscopeResponse

    @GET("api/v1/get-horoscope/monthly")
    suspend fun getMonthlyHoroscope(@Query("sign") sign: String): HoroscopeResponse

    companion object {
        private const val BASE_URL = "https://freehoroscopeapi.com/"

        fun create(): HoroscopeApiService {
            return Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                    .create(HoroscopeApiService::class.java)
        }
    }
}
