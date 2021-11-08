package com.mehmetalioyur.forecastapplication.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehmetalioyur.forecastapplication.ForecastApplication
import com.mehmetalioyur.forecastapplication.model.ForecastModel
import com.mehmetalioyur.forecastapplication.repo.ForecastRepository
import com.mehmetalioyur.forecastapplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: ForecastRepository,
    application: Application
) : AndroidViewModel(application) {

    var forecastValues = MutableLiveData<Resource<ForecastModel>>()

    fun getForecastValues(latitude: String, longitude: String) = viewModelScope.launch {
        safeForecastValues(latitude,longitude)

    }

    private fun handleForecastValuesResponse(response: Response<ForecastModel>): Resource<ForecastModel> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeForecastValues(latitude: String, longitude: String) {
        forecastValues.postValue(Resource.Loading())


        try {
            if (hasInternetConnection()) {
                val response = repository.getDatas(latitude, longitude)
                forecastValues.postValue(handleForecastValuesResponse(response))
            } else {
                forecastValues.postValue(Resource.Error("İnternet bağlantınızı kontrol ediniz."))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> forecastValues.postValue(Resource.Error("İnternet bağlantınızı kontrol ediniz."))
                else -> forecastValues.postValue(Resource.Error("Hava durumu verileri alınırken hata oluştu. "))
            }

        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<ForecastApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }

        } else {
            connectivityManager.activeNetworkInfo.run {
                return when (this!!.type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }

        }
    }

}







