package co.si.main.auth.login.networking

import ResponseState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.si.main.auth.login.data.ReqSendOtp
import co.si.main.auth.login.data.ReqVerifyPassword
import co.si.main.auth.login.data.ResVerifyPassword
import corp.hell.kernel.parent.BaseViewModel
import corp.hell.kernel.utils.toJsonString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since September 08, 2021
 */
@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var repo: LoginRepository
    fun init() {
        sendOtp()
        callMockAPI()
        getSmartEysOnBoardingData()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Setting Mock Demo
    ///////////////////////////////////////////////////////////////////////////
    private fun sendOtp() = viewModelScope.launch {
        Timber.d("callMockAPI mock api otp called")
        callAPIAndPreprocessResponse({
            repo.sendOtp(
                ReqSendOtp(
                    "91",
                    "9457825354",
                    "IN",
                )
            )
        }) { state ->
            when {
                state is ResponseState.LoadingState -> {
                    showLoader()
                }

                state is ResponseState.SuccessState -> {
                    Timber.d("callMockAPI mock api otp SuccessState data ${toJsonString(state.res.data)}")
                }

                state is ResponseState.ErrorState -> {
                    Timber.d("callMockAPI mock api otp ErrorState message ${toJsonString(state.message)}")
                    hideLoader()
                    showSnackbarError(state.message)
                }
            }
        }

    }


    private fun callMockAPI() = viewModelScope.launch {
        Timber.d("callMockAPI mock api called")
        callAPIAndPreprocessResponse({ repo.callMockAPI() }) { state ->
            Timber.d("callMockAPI mock api state ${toJsonString(state)}")
            Timber.d("callMockAPI ${toJsonString(state)}")
            when {
                state is ResponseState.LoadingState -> {
                    showLoader()
                }

                state is ResponseState.SuccessState -> {
                    Timber.d("callMockAPI mock api SuccessState data ${toJsonString(state.res.data)}")
                }

                state is ResponseState.ErrorState -> {
                    Timber.d("callMockAPI mock api ErrorState message ${toJsonString(state.message)}")
                    hideLoader()
                    showSnackbarError(state.message)
                }
            }
        }

    }

    private fun getSmartEysOnBoardingData() = viewModelScope.launch {
        Timber.d("callMockAPI getSmartEysOnBoardingData api called")
        callAPIAndPreprocessResponse({ repo.getSmartEysOnBoardingData() }) { state ->
            Timber.d("callMockAPI getSmartEysOnBoardingData ${toJsonString(state)}")
            when {
                state is ResponseState.LoadingState -> {
                    showLoader()
                }

                state is ResponseState.SuccessState -> {
                    Timber.d(
                        "callMockAPI getSmartEysOnBoardingData SuccessState data ${
                            toJsonString(
                                state.res.data
                            )
                        }"
                    )
                }

                state is ResponseState.ErrorState -> {
                    Timber.d(
                        "callMockAPI getSmartEysOnBoardingData ErrorState message ${
                            toJsonString(
                                state.message
                            )
                        }"
                    )
                    hideLoader()
                    showSnackbarError(state.message)
                }
            }
        }

    }

    ///////////////////////////////////////////////////////////////////////////
    // Check User Existence
    ///////////////////////////////////////////////////////////////////////////
    private val resPasswordVerifyMutableLiveData: MutableLiveData<ResVerifyPassword> =
        MutableLiveData()
    val resVerifyPasswordLiveData: LiveData<ResVerifyPassword>
        get() = resPasswordVerifyMutableLiveData


    fun updateUserData(data: ResVerifyPassword) {
        resPasswordVerifyMutableLiveData.value = data
    }

    fun callPasswordVerifyAPI(email: String, password: String) = viewModelScope.launch {
        val req = ReqVerifyPassword(
            osVersion = "12",
            email = email,
            password = password,
            deviceId = "12112121",
            deviceName = "MOTO G",
            ipAddress = "13:121:222:0",
            appVersion = "1"
        )
        callAPIAndPreprocessResponse({ repo.passwordVerifyAPI(req) }) { state ->
            when {
                state is ResponseState.LoadingState -> {
                    showLoader()
                }

                state is ResponseState.SuccessState -> {
                    resPasswordVerifyMutableLiveData.postValue(state.res!!)
                    showSnackbarSuccess(state.res.message)
                }

                state is ResponseState.ErrorState -> {
                    hideLoader()
                    showSnackbarError(state.message)
                }
            }
        }

    }

}