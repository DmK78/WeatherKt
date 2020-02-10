package ru.job4j.weatherkt

import android.app.Application
import ru.job4j.weatherkt.di.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerAppComponent.builder().networkServiceModule(NetworkServiceModule())
                .placePreferencesModule(PlacePreferencesModule(applicationContext))
                .locationModule(LocationModule(applicationContext)).build()
    }

    companion object {
        private lateinit var appComponent: AppComponent
        val component: AppComponent
            get() = appComponent
    }
}