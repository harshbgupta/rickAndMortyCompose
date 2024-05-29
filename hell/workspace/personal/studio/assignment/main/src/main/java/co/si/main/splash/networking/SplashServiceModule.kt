package co.si.main.splash.networking

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since September 06, 2021
 */
@Module
@InstallIn(SingletonComponent::class)
object SplashServiceModule {

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): SplashService {
        return retrofit.create(SplashService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(splashService: SplashService): SplashRepository =
        SplashRepository(splashService)
}