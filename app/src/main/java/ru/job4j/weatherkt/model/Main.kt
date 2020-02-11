package ru.job4j.weatherkt.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */
data class Main (
    val temp: Float = 0f,
    val pressure: Float = 0f,
    val humidity: Float = 0f,
    @SerializedName("temp_min")
    var minTemp: Float = 0f,
    @SerializedName("temp_max")
    var maxTemp: Float = 0f
)