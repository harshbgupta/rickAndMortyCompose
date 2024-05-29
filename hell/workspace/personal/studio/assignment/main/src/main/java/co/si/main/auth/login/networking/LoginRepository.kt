package co.si.main.auth.login.networking

import co.si.main.auth.login.data.ReqSendOtp
import co.si.main.auth.login.data.ReqVerifyPassword
import corp.hell.kernel.network.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since September 08, 2021
 */
class LoginRepository @Inject constructor(val api: LoginApi) : NetworkRepository() {


    suspend fun sendOtp(req: ReqSendOtp) =
        requestAPIFlow { api.sendOtp(req) }.flowOn(Dispatchers.IO)

    suspend fun passwordVerifyAPI(req: ReqVerifyPassword) = requestAPIFlow(
        mockApiEnabled = true, "json/auth/apiVerifyPassword.json"
    ) { api.apiVerifyPassword(req) }.flowOn(Dispatchers.IO)

    suspend fun callMockAPI() = requestAPIFlow(
        mockApiEnabled = true, "json/auth/getMockData.json"
    ) { api.callMockAPI() }.flowOn(Dispatchers.IO)

    suspend fun getSmartEysOnBoardingData() = requestAPIFlow(
        mockApiEnabled = true, "json/auth/getSmartEysOnBoardingData.json"
    ) { api.getSmartEysOnBoardingData() }.flowOn(Dispatchers.IO)

}