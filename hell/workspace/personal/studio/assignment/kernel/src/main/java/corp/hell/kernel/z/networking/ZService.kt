package corp.hell.kernel.z.networking

import corp.hell.kernel.z.data.ReqZ
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since September 06, 2021
 */
interface ZService {

    @GET("open/sendOtp/{phone}")
    fun getApi(@Path("phone") phone: String): Call<JsonElement>

    @POST("open/verifyOtp")
    fun postApi(@Body req: ReqZ): Call<JsonElement>
}