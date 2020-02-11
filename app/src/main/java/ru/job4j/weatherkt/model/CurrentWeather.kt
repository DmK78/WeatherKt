package ru.job4j.weatherkt.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */
data class CurrentWeather(
    val main: Main? = null,
    val weather: List<Weather>? = null,
    val wind: Wind? = null,
    val sys: Sys? = null,
    val dt: Int? = null,
    var latLng: LatLng? = null,
    @SerializedName("name")
    var cityName: String? = null
) {}