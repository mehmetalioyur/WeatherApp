package com.mehmetalioyur.forecastapplication.repo

import com.bumptech.glide.load.engine.Resource
import com.mehmetalioyur.forecastapplication.api.RetrofitAPI
import com.mehmetalioyur.forecastapplication.model.ForecastModel
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val api: RetrofitAPI
) {

    suspend fun getDatas(latitude: String, longitude : String) =api.getDailyForecast(latitude,longitude)
}