package ru.job4j.weatherkt.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.job4j.weatherkt.utils.Constants
import ru.job4j.weatherkt.utils.PlacePreferences
import ru.job4j.weatherkt.utils.PlacePreferencesImpl
import javax.inject.Singleton

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