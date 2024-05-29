package co.si.main.splash.networking

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since September 06, 2021
 */
interface SplashService {
    @GET("rest/aws/getKey/{userId}")
    fun getAWS(@Path("userId") userId: Long): Call<JsonElement>

    @GET("open/getRole/{userId}")
    fun getRoleDetails(@Path("userId") userId: Long): Call<JsonElement>
}