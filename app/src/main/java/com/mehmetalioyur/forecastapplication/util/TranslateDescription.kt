package com.mehmetalioyur.forecastapplication.util


class TranslateDescription {

    fun weatherTranslate(): HashMap<String, String> {
        val weatherTranslate = hashMapOf<String, String>()
        weatherTranslate["Clear"] = "Güneşli"
        weatherTranslate["Clouds"] = "Bulutlu"
        weatherTranslate["Snow"] = "Karlı"
        weatherTranslate["Rain"] = "Yağmurlu"
        weatherTranslate["Drizzle"] = "Çisentili"
        weatherTranslate["Thunderstorm"] = "Fırtınalı"
        weatherTranslate["Mist"] = "Sisli"
        weatherTranslate["Smoke"] = "Sisli"
        weatherTranslate["Haze"] = "Puslu"
        weatherTranslate["Dust"] = "Tozlu"
        weatherTranslate["Fog"] = "Sisli"
        weatherTranslate["Sand"] = "Kumlu"
        weatherTranslate["Dust"] = "Tozlu"
        weatherTranslate["Ash"] = "Küllü"
        weatherTranslate["Squall"] = "Fırtınalı"
        weatherTranslate["Tornado"] = "Kasırgalı"

        return weatherTranslate
    }
}