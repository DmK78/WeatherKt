package ru.job4j.weatherkt.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.job4j.weatherkt.utils.MyLocationImpl
import javax.inject.Singleton

@Module
class LocationModule(private val context: Context) {
    @Singleton
    @Provides
    fun providesLocationService(): MyLocationImpl {
        return MyLocationImpl(context)
    }

}