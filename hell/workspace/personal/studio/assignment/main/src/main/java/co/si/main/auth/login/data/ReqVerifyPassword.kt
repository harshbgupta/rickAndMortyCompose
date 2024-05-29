package co.si.main.auth.login.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReqVerifyPassword(
    var email: String,
    var password: String,
    var osVersion: String?,
    var deviceId: String?,
    var deviceName: String?,
    var appVersion: String,
    var ipAddress: String
) : Parcelable

data class ReqSendOtp(
    val countryCode: String,
    val phone: String,
    val regionCode: String?
)

data class SendOtpData(
    val userState: Int,
)