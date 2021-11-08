package com.mehmetalioyur.forecastapplication.util

import java.text.SimpleDateFormat
import java.util.*

class TransformDateAndTime {

   fun transformTime (time: Long): String {
           val transformedTime =
               SimpleDateFormat("HH:mm").format(Date(time * 1000))
        return transformedTime
   }
    fun transformDate(date: Long) : String {


        val transformedDate =
            SimpleDateFormat("dd MMMM yyyy    HH:mm").format(Date(date * 1000))
        return transformedDate
    }
}