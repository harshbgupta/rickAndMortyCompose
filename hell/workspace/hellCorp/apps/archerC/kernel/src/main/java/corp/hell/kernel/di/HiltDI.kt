package corp.hell.kernel.di

import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import corp.hell.kernel.BuildConfig
import corp.hell.kernel.constants.AppData.PREF_NAME
import corp.hell.kernel.constants.AppData.ctx
import corp.hell.kernel.constants.URL_BASE
import corp.hell.kernel.constants.UserData.mAuthToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
object OthersDI {
    @Provides
    @Singleton
    fun profileSharedPref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            PREF_NAME, Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext app: Context): Context {
        return app
    }
}


@Module
@InstallIn(SingletonComponent::class)
object NetworkingDI {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(3600, TimeUnit.SECONDS)
            .readTimeout(3600, TimeUnit.SECONDS)
            .writeTimeout(3600, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .apply {
                        mAuthToken?.let {
                            addHeader("token", it)
                        }
                    }
                    .build()
                chain.proceed(request)
            }
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val chuckerInterceptor = ChuckerInterceptor.Builder(ctx)
                .collector(ChuckerCollector(ctx))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
            client.addInterceptor(chuckerInterceptor)
            client.addInterceptor(loggingInterceptor)
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(URL_BASE).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @Named("retrofit_identifier") //call it as -> @Named("retrofit_identifier") val retrofit: Retrofit
    fun provideRetrofitNamed(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("DIFFERENT_BASE_URL").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}
