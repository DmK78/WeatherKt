package ru.job4j.weatherkt.di

import dagger.Component
import ru.job4j.weatherkt.weather.WeatherViewModel
import javax.inject.Singleton

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 10.02.2019
 */

@Singleton
@Component(modules = [NetworkServiceModule::class, PlacePreferencesModule::class, LocationModule::class])
interface AppComponent {
    fun injectTo(weatherViewModel: WeatherViewModel?)
}