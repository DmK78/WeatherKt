package ru.job4j.weatherkt.model

import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */
data class FiveDaysWeather (
    @SerializedName("list")
    var days: List<Day>? = null,
    var city: City? = null){

    fun calculateDateTime() {

        for (day in this.days!!) {
            day.time = day.dt_txt.substring(10, day.dt_txt.length - 3)
            day.date = day.dt_txt.substring(8, 10) + "-" + day.dt_txt.substring(
                5,
                7
            ) + "-" + day.dt_txt.substring(0, 4)
            val fromUser = SimpleDateFormat("dd-MM-yyyy")
            val myFormat = SimpleDateFormat("dd MMM, EE")
            try {
                val reformattedStr = myFormat.format(fromUser.parse(day.date))
                day.date = reformattedStr
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }
}