package ru.job4j.weatherkt.di

import dagger.Component
import ru.job4j.weatherkt.weather.WeatherViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkServiceModule::class, PlacePreferencesModule::class, LocationModule::class])
interface AppComponent {
    fun inject(weatherViewModel: WeatherViewModel?)
}