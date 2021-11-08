package com.mehmetalioyur.forecastapplication.api

import com.mehmetalioyur.forecastapplication.model.ForecastModel
import com.mehmetalioyur.forecastapplication.util.Constants.API_KEY
import com.mehmetalioyur.forecastapplication.util.Constants.LANGUAGE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    //api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}

    @GET("data/2.5/weather")
    suspend fun getDailyForecast(


        @Query("lat") latitude : String,
        @Query("lon") longitude: String,
        @Query("lang") language :String = LANGUAGE,
        @Query("appid") apiKey : String = API_KEY
    ): Response<ForecastModel>


}