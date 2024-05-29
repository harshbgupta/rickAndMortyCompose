package co.si.main.auth.login.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ResVerifyPassword(
    val successful: Boolean,
    val httpsStatus: Int,
    val message: String,
    val error: String?,
    val timestamp: Long,
    val result: TokenResult?
) : Parcelable

@Parcelize
data class TokenResult(
    val token: String, val user: User
) : Parcelable

@Parcelize
data class User(
    val id: Long,
    val userProductId: Long,
    val subscriberId: Long,
    val firstName: String,
    val lastName: String,
    val userType: String?,
    val name: String?,
    val signUpNumber: String?,
    val email: String,
    val osVersion: String?,
    val deviceId: String?,
    val deviceName: String?,
    val appVersion: String?,
    val ipAddress: String?,
    val token: String?
) : Parcelable

@Parcelize
data class Picsum(
    val author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
) : Parcelable

@Parcelize
data class SmartEyeOnBoardingData(
    val camera_allotted: Int,
    val duration: List<Duration>,
    val offer_info: String,
    val timings: List<Timing>
) : Parcelable

@Parcelize
data class SmartEyeOnBoardingMeta(
    val feature_video_links: @RawValue List<Any>,
    val price: String,
    val user_feedback_links: @RawValue List<Any>
) : Parcelable

@Parcelize
data class Duration(
    val display_value: String, val type: String, val value: Int
) : Parcelable

@Parcelize
data class Timing(
    val display_title: String, val display_type_info: String, val type: String
) : Parcelable