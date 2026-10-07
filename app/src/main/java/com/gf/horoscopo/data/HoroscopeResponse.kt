package com.gf.horoscopo.data

import com.google.gson.annotations.SerializedName

data class HoroscopeResponse(@SerializedName("data") val data: HoroscopeApiResponse)

data class HoroscopeApiResponse(@SerializedName("date") val date: String,
                                @SerializedName("period") val period: String,
                                @SerializedName("sign") val sign: String,
                                @SerializedName("horoscope") val horoscope: String)
