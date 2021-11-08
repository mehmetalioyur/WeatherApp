package com.mehmetalioyur.forecastapplication.util

import java.lang.Math.ceil

class KelvinToCelsius{

    fun kelvinToCelsius(kelvin : Double): Double {
    var celsius = kelvin-273.15
        celsius = kotlin.math.ceil(celsius * 10) / 10

    return celsius
}
}