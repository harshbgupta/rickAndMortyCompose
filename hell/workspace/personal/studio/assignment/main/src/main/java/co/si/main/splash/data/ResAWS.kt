package co.si.main.splash.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResAWS(
    val error: String,
    val httpStatus: Int,
    val message: String,
    val result: Result?,
    val successful: Boolean,
    val timestamp: String
) : Parcelable {
    @Parcelize
    data class Result(
        val key: String,
        val secret: String
    ) : Parcelable
}
