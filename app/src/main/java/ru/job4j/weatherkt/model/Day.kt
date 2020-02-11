package ru.job4j.weatherkt.model

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */
data class Day (
    var main: Main,
    var weather: List<Weather>,
    var wind: Wind,
    var dt_txt: String,
    var date: String,
    var time: String

)