package ru.job4j.weatherkt.model

import java.util.*

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */
data class Weather (
    var id: Int? = null,
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null

)