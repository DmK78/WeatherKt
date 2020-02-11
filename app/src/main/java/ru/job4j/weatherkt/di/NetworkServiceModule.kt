package ru.job4j.weatherkt.di

import dagger.Module
import dagger.Provides
import ru.job4j.weatherkt.network.NetworkService
import javax.inject.Singleton

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 10.02.2019
 */

@Module
class NetworkServiceModule {
    @Singleton
    @Provides
    fun providesNetworkService(): NetworkService {
        return NetworkService()
    }
}