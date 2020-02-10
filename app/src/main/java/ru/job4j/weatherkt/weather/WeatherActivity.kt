package ru.job4j.weatherkt.weather

import androidx.fragment.app.Fragment
import ru.job4j.weatherkt.BaseActivity

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */
class WeatherActivity : BaseActivity() {
    override fun createFragment(): Fragment? {
        return WeatherFragment()
    }
}