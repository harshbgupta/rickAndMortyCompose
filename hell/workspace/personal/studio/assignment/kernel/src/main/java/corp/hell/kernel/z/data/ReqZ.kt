package corp.hell.kernel.z.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since September 27, 2021
 */
@Parcelize
data class ReqZ(
    val lang: String?,
    val otp: String?,
    val phone: String?
) : Parcelable