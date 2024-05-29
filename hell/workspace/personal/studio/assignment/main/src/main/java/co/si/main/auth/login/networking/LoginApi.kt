package co.si.main.auth.login.networking

import co.si.main.auth.login.data.Picsum
import co.si.main.auth.login.data.ReqSendOtp
import co.si.main.auth.login.data.ReqVerifyPassword
import co.si.main.auth.login.data.ResVerifyPassword
import co.si.main.auth.login.data.SendOtpData
import co.si.main.auth.login.data.SmartEyeOnBoardingData
import corp.hell.kernel.network.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since September 06, 2021
 */
interface LoginApi {

    @POST("v1/auth/otp/send")
    suspend fun sendOtp(@Body req: ReqSendOtp): Response<BaseResponse<SendOtpData>>

    @POST("open/valuer/verifyUser")
    suspend fun apiVerifyPassword(@Body req: ReqVerifyPassword): Response<ResVerifyPassword>

    @POST("open/valuer/verifyUser")
    suspend fun callMockAPI(): Response<BaseResponse<List<Picsum>>>

    @POST("open/valuer/verifyUser")
    suspend fun getSmartEysOnBoardingData(): Response<BaseResponse<SmartEyeOnBoardingData>>
}