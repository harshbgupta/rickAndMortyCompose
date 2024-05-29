package corp.hell.kernel.z.networking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import corp.hell.kernel.z.data.ResZ
import com.google.gson.Gson
import com.google.gson.JsonElement
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Copyright © 2022 Hell Corporation. All rights reserved.
 *
 * @author Harsh Gupta aka Lucifer 😈
 * @since January 14, 2022
 */
@HiltViewModel
class ZViewModel @Inject constructor(private val repo: ZRepo) : ViewModel() {

    ///////////////////////////////////////////////////////////////////////////
    // First APi Demo
    ///////////////////////////////////////////////////////////////////////////
    private val sendOtpMutableLiveData: MutableLiveData<ResZ> = MutableLiveData()
    val sendOtpLiveData: LiveData<ResZ>
        get() = sendOtpMutableLiveData

    fun updateUserData(data: ResZ) {
        sendOtpMutableLiveData.value = data
    }

    fun apiCallMethodName(phone: String) {
        repo.methodName1(phone).enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                val res = Gson().fromJson(response.body().toString(), ResZ::class.java)
                sendOtpMutableLiveData.postValue(res)

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}