package corp.hell.kernel.z.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ResZ(
    var error: String?,
    var httpStatus: Int,
    var message: String,
    var result: @RawValue Any?,
    var successful: Boolean,
    var timestamp: String
) : Parcelable