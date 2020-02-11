package ru.job4j.weatherkt.model

import com.google.gson.annotations.SerializedName

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */
data class City (
    val id: Int? = null,
    val name: String? = null,
    val coord: Coord? = null,
    val country: String? = null,
    val population: Int? = null,
    @SerializedName("timezone")
    val timeZone: Int? = null,
    val sunrise: Int? = null,
    val sunset: Int? = null
)
