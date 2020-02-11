package ru.job4j.weatherkt.location

import android.location.Location

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */
interface MyLocation {
    val location: Location?
}