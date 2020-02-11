package ru.job4j.weatherkt.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */
data class Wind (
    val speed: Float = 0f,
    @SerializedName("deg")
    val degree: Float = 0f
)