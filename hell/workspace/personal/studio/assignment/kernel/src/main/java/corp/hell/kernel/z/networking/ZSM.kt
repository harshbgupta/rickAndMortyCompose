package corp.hell.kernel.z.networking

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
 * @since September 22, 2021
 */

@Module
@InstallIn(SingletonComponent::class)
object ZSM {

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ZService {
        return retrofit.create(ZService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(service: ZService): ZRepo =
        ZRepo(service)
}