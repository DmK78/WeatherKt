package ru.job4j.weatherkt.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.job4j.weatherkt.location.MyLocationImpl
import javax.inject.Singleton

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 10.02.2019
 */

@Module
class LocationModule(private val context: Context) {
    @Singleton
    @Provides
    fun providesLocationService(): MyLocationImpl {
        return MyLocationImpl(context)
    }

}