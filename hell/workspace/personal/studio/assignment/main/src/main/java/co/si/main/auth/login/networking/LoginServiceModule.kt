package co.si.main.auth.login.networking

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since September 06, 2021
 */
@Module
@InstallIn(SingletonComponent::class)
object LoginServiceModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(api: LoginApi): LoginRepository =
        LoginRepository(api)
}