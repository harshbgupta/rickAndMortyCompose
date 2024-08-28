package corp.hell.kernel.constants

import android.annotation.SuppressLint
import android.content.Context
import corp.hell.kernel.parent.imp.LiveEvent

/**
 * Copyright © 2019 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since February 16, 2012
 */
/******************************************** Variables  ******************************************/
//App level vars

@SuppressLint("StaticFieldLeak")
object AppData {

    // Shared Pref
    const val PREF_NAME = "PREF"
    const val BUILD_FLAVOR_DEV = "dev"
    const val BUILD_FLAVOR_QA = "qa"
    const val BUILD_FLAVOR_PROD = "prod"
    const val APP_STORE_URL_PREFIX = "https://play.google.com/store/apps/details?id="

    @Volatile
    lateinit var ctx: Context

    @Volatile
    var mPackageName: String? = null

    @Volatile
    var mVersionName: String? = null

    @Volatile
    var mVersionCode: Int? = null

    @Volatile
    var mLanguage: String? = "en"
}


//Others
//User level vars
object UserData {

    @Volatile
    var mAuthToken: String? = null

    @Volatile
    var mUserName: String? = null

    @Volatile
    var mUserId: Long = -1

    @Volatile
    var mCountryCode: String? = null

    @Volatile
    var mMobileNumber: String? = null

    @Volatile
    var mFirstName: String? = null

    @Volatile
    var mLastName: String? = null

    @Volatile
    var mFullName: String? = null

    @Volatile
    var mProfilePic: String? = null

    @Volatile
    var mProfileProgress = 1
}

//Device level vars
object DeviceData {

    @Volatile
    var mDeviceToken: String? = null

    var deviceModel: String = android.os.Build.MODEL

    @Volatile
    var deviceId: String? = null

    @Volatile
    var deviceOsVersion: String? = null

    @Volatile
    var deviceIMEINo: String? = null

    @Volatile
    var deviceIpAddress: String? = null
}

//Other level vars
object OtherData {
    //crash listener/updater
    var showCrashPopUp = LiveEvent<Long>()
}

//some by default args constants
object Args {
    const val ARG_PARAM1 = "param1"
    const val ARG_PARAM2 = "param2"
    const val ARG_PARAM3 = "param3"
    const val ARG_PARAM4 = "param4"
    const val ARG_PARAM5 = "param5"
    const val ARG_PARAM6 = "param6"
    const val ARG_PARAM7 = "param7"
    const val ARG_PARAM8 = "param8"
    const val ARG_PARAM9 = "param9"
}