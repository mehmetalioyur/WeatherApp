package com.mehmetalioyur.forecastapplication.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mehmetalioyur.forecastapplication.databinding.FragmentCurrentForecastBinding
import com.mehmetalioyur.forecastapplication.util.Resource
import com.mehmetalioyur.forecastapplication.viewmodel.ForecastViewModel
import com.mehmetalioyur.forecastapplication.util.KelvinToCelsius
import com.mehmetalioyur.forecastapplication.util.TransformDateAndTime
import com.mehmetalioyur.forecastapplication.util.TranslateDescription


class CurrentForecastFragment : Fragment() {

    private var _binding: FragmentCurrentForecastBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ForecastViewModel
    private lateinit var sharedPreferences: SharedPreferences




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(
            "com.mehmetalioyur.forecastapplication",
            Context.MODE_PRIVATE
        )
        val latitude = sharedPreferences.getString("latitude", "40")
        val longitude = sharedPreferences.getString("longitude", "40")


        viewModel = ViewModelProvider(requireActivity())[ForecastViewModel::class.java]
        viewModel.getForecastValues(latitude!!, longitude!!)  //default value var, boş gelmez
        observeLiveData()

    }

    fun observeLiveData() {
        viewModel.forecastValues.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    showScreen()
                    printTheValues()

                }
                is Resource.Loading ->{
                    showProgressBar()
                    hideErrorMessage()
                    hideScreen()

                }
                is Resource.Error -> {
                    hideScreen()
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity,"Bir hata farkedildi : $it",Toast.LENGTH_LONG).show()
                    }
                }
            }

        })

    }



    private fun hideScreen(){
        binding.mainContainer.visibility = View.GONE
    }

    private fun showScreen(){
        binding.mainContainer.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideErrorMessage() {
        binding.errorTextView.visibility = View.GONE
    }

    private fun showErrorMessage(message: String) {
        binding.errorTextView.visibility = View.VISIBLE
        binding.errorTextView.text = message

    }


    @SuppressLint("SetTextI18n")
    private fun printTheValues() {
        viewModel.forecastValues.value!!.data.apply {
            this?.let {




            binding.address.text = "${it.name} ,${it.sys.country}"
            binding.tempMin.text =
                "En Düşük : ${KelvinToCelsius().kelvinToCelsius(it.main.temp_min)} °C "
            binding.tempMax.text =
                "En Yüksek : ${KelvinToCelsius().kelvinToCelsius(it.main.temp_max)} °C"
            binding.currentTemperature.text =
                "${KelvinToCelsius().kelvinToCelsius(it.main.temp)} °C"
            binding.updatedTime.text =
                "Son Güncellenme Zamanı ${TransformDateAndTime().transformDate(it.dt)}"

                Glide.with(requireView())
                    .load("https://openweathermap.org/img/wn/" + it.weather[0].icon + "@4x.png")
                    .into(binding.forecastPng   )


            binding.status.text = TranslateDescription().weatherTranslate()[it.weather[0].main]
            binding.groundLevel.text = "${it.main.grnd_level}"
            binding.sunrise.text = TransformDateAndTime().transformTime(it.sys.sunrise)
            binding.sunset.text = TransformDateAndTime().transformTime(it.sys.sunset)
            binding.wind.text = "${it.wind.speed} km"
            binding.pressure.text = "${it.main.pressure}"
            binding.humidity.text = "% ${it.main.humidity}"
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}