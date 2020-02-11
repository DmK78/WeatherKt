package ru.job4j.weatherkt.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.job4j.weatherkt.utils.Constants
import ru.job4j.weatherkt.sharedprefs.PlacePreferences
import ru.job4j.weatherkt.sharedprefs.PlacePreferencesImpl
import javax.inject.Singleton

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 10.02.2019
 */

@Module
class PlacePreferencesModule(private val context: Context) {
    @Singleton
    @Provides
    fun providesPlacePreferences(context: Context?): PlacePreferences {
        return PlacePreferencesImpl(context)
    }

    @Provides
    fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(
            Constants.MY_SETTINGS,
            Context.MODE_PRIVATE
        )
    }

}