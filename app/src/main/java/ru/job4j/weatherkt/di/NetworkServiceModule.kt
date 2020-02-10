package ru.job4j.weatherkt.di

import dagger.Module
import dagger.Provides
import ru.job4j.weatherkt.network.NetworkService
import javax.inject.Singleton

@Module
class NetworkServiceModule {
    @Singleton
    @Provides
    fun providesNetworkService(): NetworkService {
        return NetworkService()
    }
}