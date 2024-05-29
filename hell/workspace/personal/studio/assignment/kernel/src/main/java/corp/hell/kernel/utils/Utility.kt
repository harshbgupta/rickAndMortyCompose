package co.si.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.text.format.Formatter
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import corp.hell.kernel.BuildConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import corp.hell.kernel.R
import corp.hell.kernel.constants.DeviceData.mDeviceToken
import corp.hell.kernel.constants.UserData
import timber.log.Timber
import java.util.Calendar
import java.util.Random


/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since August 15, 2021
 */

/**
 * Loads Image into ImageView from androidResPath from activity
 *
 * @param imageView         Image view
 * @param androidResPath    android resource path
 * @param makeCircularImage true or false true for making image circular
 */
fun Context.loadImage(imageView: ImageView, androidResPath: Int, makeCircularImage: Boolean) {
    try {
        val builder: RequestBuilder<Drawable> =
            Glide.with(this).load(androidResPath)
        if (makeCircularImage) builder.apply(RequestOptions.circleCropTransform())
        builder.into(imageView)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

/**
 * Loads Image into ImageView from androidResPath from fragment
 *
 * @param imageView         Image view
 * @param androidResPath    android resource path
 * @param makeCircularImage true or false true for making image circular
 */
fun Fragment.loadImage(imageView: ImageView, androidResPath: Int, makeCircularImage: Boolean) {
    try {
        val builder: RequestBuilder<Drawable> =
            Glide.with(this).load(androidResPath)
        if (makeCircularImage) builder.apply(RequestOptions.circleCropTransform())
        builder.into(imageView)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

/**
 * Loads Image into ImageView from Path from activity
 *
 * @param imageView         Image view
 * @param placeholder       placeholder drawable or mipmap image file or null
 * @param path              URL path
 * @param signature         signature key whenever a image change its need to be changed
 * @param makeCircularImage true or false true for making image circular
 */
fun Context.loadImage(
    imageView: ImageView,
    path: String,
    signature: String?,
    placeholder: Int?,
    makeCircularImage: Boolean
) {
    try {
        val builder: RequestBuilder<Drawable> = Glide.with(this).load(path)
        if (signature != null) builder.signature(ObjectKey(signature))
        if (makeCircularImage) builder.apply(RequestOptions.circleCropTransform())
        if (placeholder != null) builder.apply(RequestOptions().placeholder(placeholder))
        builder.into(imageView)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

/**
 * Loads Image into ImageView from Path from fragment
 *
 * @param imageView         Image view
 * @param placeholder       placeholder drawable or mipmap image file or null
 * @param path              URL path
 * @param signature         signature key whenever a image change its need to be changed
 * @param makeCircularImage true or false true for making image circular
 */
fun Fragment.loadImage(
    imageView: ImageView,
    path: String,
    signature: String?,
    placeholder: Int?,
    makeCircularImage: Boolean
) {
    try {
        val builder: RequestBuilder<Drawable> = Glide.with(this).load(path)
        if (signature != null) builder.signature(ObjectKey(signature))
        if (makeCircularImage) builder.apply(RequestOptions.circleCropTransform())
        if (placeholder != null) builder.apply(RequestOptions().placeholder(placeholder))
        builder.into(imageView)
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

/**
 * Loads Image into ImageView from byte array from activity
 *
 * @param byteArray   Image view
 * @param placeholder placeholder drawable or mipmap image file or null
 * @param imageView   byte array of image
 * @signature signature key whenever a image change its need to be changed
 */
fun Context.loadImage(
    imageView: ImageView,
    byteArray: ByteArray,
    signature: String?,
    placeholder: Int?,
    makeCircularImage: Boolean
) {
    try {
        val builder: RequestBuilder<Drawable> = Glide.with(this).load(byteArray)
        if (signature != null) builder.signature(ObjectKey(signature))
        if (makeCircularImage) builder.apply(RequestOptions.circleCropTransform())
        if (placeholder != null) builder.apply(RequestOptions().placeholder(placeholder))
        builder.into(imageView)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

/**
 * Loads Image into ImageView from byte array from Fragment
 *
 * @param byteArray   Image view
 * @param placeholder placeholder drawable or mipmap image file or null
 * @param imageView   byte array of image
 * @signature signature key whenever a image change its need to be changed
 */
fun Fragment.loadImage(
    imageView: ImageView,
    byteArray: ByteArray,
    signature: String?,
    placeholder: Int?,
    makeCircularImage: Boolean
) {
    try {
        val builder: RequestBuilder<Drawable> = Glide.with(this).load(byteArray)
        if (signature != null) builder.signature(ObjectKey(signature))
        if (makeCircularImage) builder.apply(RequestOptions.circleCropTransform())
        if (placeholder != null) builder.apply(RequestOptions().placeholder(placeholder))
        builder.into(imageView)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

/**
 * Closes soft keyboard from activity
 *
 * @param view view
 */
fun Context.closeKeyboard(view: View) {
    try {
        val mgr = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mgr.hideSoftInputFromWindow(view?.windowToken, 0)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

/**
 * Closes soft keyboard from Fragment
 *
 * @param view view
 */
fun Fragment.closeKeyboard(view: View) {
    try {
        val mgr = this.requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mgr.hideSoftInputFromWindow(view?.windowToken, 0)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

/**
 * Shows warning msg as red background color from activity
 *
 * @param message         msg to be shown
 * @param backGroundColor background color
 */
fun Context.snackBar(message: String?, backGroundColor: Int?) {
    try {
        val rootView = (this as FragmentActivity).findViewById<View>(android.R.id.content)
        val snackBar = Snackbar.make(
            rootView,
            message!!, Snackbar.LENGTH_LONG
        )
        snackBar.setActionTextColor(ContextCompat.getColor(this, R.color.color_white_1000))
        val snackBarView = snackBar.view
        if (backGroundColor != null) {
            snackBarView.setBackgroundColor(ContextCompat.getColor(this, backGroundColor))
        } else {
            snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.md_theme_primary))
        }
        snackBar.show()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

/**
 * Shows warning msg as red background color from Fragment
 *
 * @param message         msg to be shown
 * @param backGroundColor background color
 */
fun Fragment.snackBar(message: String?, backGroundColor: Int?) {
    try {
        val rootView = (this.requireActivity()).findViewById<View>(android.R.id.content)
        val snackBar = Snackbar.make(
            rootView,
            message!!, Snackbar.LENGTH_LONG
        )
        snackBar.setActionTextColor(
            ContextCompat.getColor(
                this.requireContext(),
                R.color.color_white_1000
            )
        )
        val snackBarView = snackBar.view
        if (backGroundColor != null) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this.requireContext(),
                    backGroundColor
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this.requireContext(),
                    R.color.md_theme_primary
                )
            )
        }
        snackBar.show()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

/**
 * Shows warning msg as red background color
 *
 * @param message         msg to be shown
 * @param backGroundColor background color
 * @param textColor       Text color
 */
fun Context.snackBar(message: String?, backGroundColor: Int?, textColor: Int?) {
    try {
        val rootView = (this as FragmentActivity).findViewById<View>(android.R.id.content)
        val snackBar = Snackbar.make(
            rootView,
            message!!, Snackbar.LENGTH_LONG
        )
        if (textColor != null) {
            snackBar.setTextColor(ContextCompat.getColor(this, textColor))
        } else {
            snackBar.setTextColor(ContextCompat.getColor(this, R.color.color_white_1000))
        }
        val snackBarView = snackBar.view
        if (backGroundColor != null) {
            snackBarView.setBackgroundColor(ContextCompat.getColor(this, backGroundColor))
        } else {
            snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.md_theme_primary))
        }
        snackBar.show()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

/**
 * Shows warning msg as red background color
 *
 * @param message         msg to be shown
 * @param backGroundColor background color
 * @param textColor       Text color
 */
fun Fragment.snackBar(message: String?, backGroundColor: Int?, textColor: Int?) {
    try {
        val rootView = (this.requireActivity()).findViewById<View>(android.R.id.content)
        val snackBar = Snackbar.make(
            rootView,
            message!!, Snackbar.LENGTH_LONG
        )
        if (textColor != null) {
            snackBar.setTextColor(ContextCompat.getColor(this.requireContext(), textColor))
        } else {
            snackBar.setTextColor(
                ContextCompat.getColor(
                    this.requireContext(),
                    R.color.color_white_1000
                )
            )
        }
        val snackBarView = snackBar.view
        if (backGroundColor != null) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this.requireContext(),
                    backGroundColor
                )
            )
        } else {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this.requireContext(),
                    R.color.md_theme_primary
                )
            )
        }
        snackBar.show()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

/**
 * Checks gps status from activity
 *
 * @return on == true
 */
fun Context.checkGpsStatus(): Boolean {
    return try {
        val manager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (statusOfGPS) {
            true
        } else {
            val intent1 = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            this.startActivity(intent1)
            false
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        false
    }
}


/**
 * Checks gps status from fragment
 *
 * @return on == true
 */
fun Fragment.checkGpsStatus(): Boolean {
    return try {
        val manager =
            this.requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (statusOfGPS) {
            true
        } else {
            val intent1 = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            this.startActivity(intent1)
            false
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        false
    }
}

/**
 * Check for email validation
 *
 * @param phoneNumber correct/ Incorrect phoneNumber
 * @return true or false based on phone number validation
 */
fun isValidPhone(phoneNumber: String): Boolean {
    return try {
        Patterns.PHONE.matcher(phoneNumber).matches()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        false
    }
}

/**
 * Check for email validation
 *
 * @param email correct/ Incorrect email
 * @return true or false based on email validation
 */
fun isValidEmail(email: CharSequence): Boolean {
    return try {
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        false
    }
}

/**
 * Convert your string byteArray (encodedBase64) to byteArray (byte[])
 * for displaying in glide load url.
 *
 * @param encoded encoded byte[] string
 * @return docedec byte[] object from encoded byte[] string
 */
fun getByteArray(encoded: String?): ByteArray? {
    return if (encoded != null) {
        try {
            Base64.decode(encoded, Base64.DEFAULT)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    } else {
        null
    }
}

/**
 * Change color of status bar by passing hex color Code form Activity
 *
 * @param hexColor color code in hexadecimal e.g. #ffffff
 */
fun Context.changeStatusColor(hexColor: String?) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
        try {
            val window = (this as FragmentActivity).window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.parseColor(hexColor)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}

/**
 * Change color of status bar by passing hex color Code from fragment
 *
 * @param hexColor color code in hexadecimal e.g. #ffffff
 */
fun Fragment.changeStatusColor(hexColor: String?) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
        try {
            val window = (this.requireActivity()).window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.parseColor(hexColor)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}

/**
 * Change color of status bar by passing color reference e.g. 0xffffff or R.color,colorPrimary from activity
 *
 * @param colorReference color reference e.g. 0xffffff or R.color,colorPrimary
 */
fun Context.changeStatusColor(colorReference: Int) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
        try {
            val window = (this as FragmentActivity).window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, colorReference)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}

/**
 * Change color of status bar by passing color reference e.g. 0xffffff or R.color,colorPrimary from fragment
 *
 * @param colorReference color reference e.g. 0xffffff or R.color,colorPrimary
 */
fun Fragment.changeStatusColor(colorReference: Int) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
        try {
            val window = (this.requireActivity()).window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this.requireActivity(), colorReference)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}

/**
 * work as back button: returns to back fragment from activity
 */
fun Context.popBackStack() {
    try {
        (this as FragmentActivity).supportFragmentManager.popBackStack()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * work as back button: returns to back fragment from fragment
 */
fun Fragment.popBackStack() {
    try {
        (this.requireActivity()).supportFragmentManager.popBackStack()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * generate random 6 digit passcode
 *
 * @return Randam 6 digit number
 */
fun randomSixDigitNumber(): String? {
    val rnd = Random()
    val number = rnd.nextInt(999999)
    // this will convert any number sequence into 6 character.
    return String.format("%06d", number)
}
//
///**
// *  Create Notification
// */
//fun Context.createNotificationChannel(): NotificationChannel? {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//        val serviceChannel = NotificationChannel(
//            mChannelId,
//            mChannelName,
//            NotificationManager.IMPORTANCE_HIGH
//        )
//
//        var description = getString(R.string.notification_channel_desc);
//        serviceChannel.description = description
//        try {
//            // Sets the notification light color for notifications posted to this
//            // channel, if the device supports this feature.
//            serviceChannel.enableLights(true)
//            serviceChannel.lightColor = Color.RED
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//        return serviceChannel
//    }
//    return null
//}

/**
 * By this method you can convert dp in to pixel. let say you need to 10dp margin in java but if you give '10' in layout params it will take
 * this as pixels so in layout params it takes as pixel so you only need to call this method passing dp value and it will return pixels that what
 * requires in layout params -> from activity
 *
 * @param dpi  DPI value needs to be converted
 * @return converted value of dpi in pixels
 */
fun Context.dpToPx(dpi: Float): Int {
    val metrics = this.resources.displayMetrics
    val fPixels = metrics.density * dpi
    return (fPixels + 0.5f).toInt()
}

/**
 * By this method you can convert dp in to pixel. let say you need to 10dp margin in java but if you give '10' in layout params it will take
 * this as pixels so in layout params it takes as pixel so you only need to call this method passing dp value and it will return pixels that what
 * requires in layout params -> from Fragment
 *
 * @param dpi  DPI value needs to be converted
 * @return converted value of dpi in pixels
 */
fun Fragment.dpToPx(dpi: Float): Int {
    val metrics = this.resources.displayMetrics
    val fPixels = metrics.density * dpi
    return (fPixels + 0.5f).toInt()
}

/**
 * Check and chow if GPS is on if not it'll show a pop up to open setting
 */
fun Context.showGPSDisabledAlertToUser() {
    val alertDialogBuilder = AlertDialog.Builder(this)
    alertDialogBuilder.setMessage(this.getString(R.string.gps_off_warning))
        .setCancelable(false)
        .setPositiveButton(
            this.getString(R.string.open_setting)
        ) { dialog, id ->
            dialog.cancel()
            val callGPSSettingIntent = Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS
            )
            this.startActivity(callGPSSettingIntent)
        }
    val alert = alertDialogBuilder.create()
    alert.show()
}

/**
 * Check and chow if GPS is on if not it'll show a pop up to open setting
 */
fun Fragment.showGPSDisabledAlertToUser() {
    val alertDialogBuilder = AlertDialog.Builder(this.requireContext())
    alertDialogBuilder.setMessage(this.getString(R.string.gps_off_warning))
        .setCancelable(false)
        .setPositiveButton(
            this.getString(R.string.open_setting)
        ) { dialog, id ->
            dialog.cancel()
            val callGPSSettingIntent = Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS
            )
            this.startActivity(callGPSSettingIntent)
        }
    val alert = alertDialogBuilder.create()
    alert.show()
}


/**
 * Setting visibility of multiple views
 */
fun setVisibilities(visibility: Int, vararg views: View) {
    views.forEach {
        it.visibility = visibility
    }
}

/**
 * Setting visibility of multiple views
 */
fun enableViews(enable: Boolean, vararg views: View) {
    views.forEach { view ->
        view.isClickable = enable
        view.isEnabled = enable
    }
}

/**
 * setting Image Url or initials from fragment
 */
fun Fragment.setImageOrInitials(
    imagePath: String?,
    firstName: String?,
    lastName: String?,
    imageView: ImageView,
    textView: TextView
) {
    if (imagePath == null) {
        if (firstName.isNullOrBlank()) {
            imageView.visibility = View.VISIBLE
            textView.visibility = View.INVISIBLE
            this.loadImage(imageView, R.drawable.ic_user_sticker, true)
        } else {
            var initials = firstName.substring(0, 1)
            if (!lastName.isNullOrBlank())
                initials += lastName.substring(0, 1)
            textView.text = initials.uppercase()
            imageView.visibility = View.INVISIBLE
            textView.visibility = View.VISIBLE
        }
    } else {
        imageView.visibility = View.VISIBLE
        textView.visibility = View.INVISIBLE
        this.loadImage(imageView, imagePath, null, R.drawable.ic_user_sticker, true)
    }
}

/**
 * setting Image Url or initials from Activity
 */
fun Context.setImageOrInitials(
    imagePath: String?,
    firstName: String?,
    lastName: String?,
    imageView: ImageView,
    textView: TextView
) {
    if (imagePath == null) {
        if (firstName.isNullOrBlank()) {
            imageView.visibility = View.VISIBLE
            textView.visibility = View.INVISIBLE
            this.loadImage(imageView, R.drawable.ic_user_sticker, true)
        } else {
            var initials = firstName.substring(0, 1)
            if (!lastName.isNullOrBlank())
                initials += lastName.substring(0, 1)
            textView.text = initials.uppercase()
            imageView.visibility = View.INVISIBLE
            textView.visibility = View.VISIBLE
        }
    } else {
        imageView.visibility = View.VISIBLE
        textView.visibility = View.INVISIBLE
        this.loadImage(imageView, imagePath, null, R.drawable.ic_user_sticker, true)
    }
}

/**
 * get Ip address
 */
fun Context.getIpAddress(): String? {
    var ip: String? = null
    try {
        val context = this.applicationContext
        val wm = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        ip = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    Timber.i("getIpAddress -> $ip")
    return ip
}

/**
 * get IMEI No
 */
@SuppressLint("MissingPermission")
fun Context.getIMEINo(): String? {
    var deviceId: String? = null
    try {
        deviceId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            "NA"
        } else {
            val mTelephony = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (mTelephony.deviceId != null) {
                mTelephony.deviceId
            } else {
                "NA"
            }
        }
    } catch (e: Throwable) {
        e.printStackTrace()
    }
    Timber.i("getIMEINo -> $deviceId")
    return deviceId
}


/**
 * Mainly Date return a proper format like 02 or 09
 * get two digits number i.e, 1 -> 01 or 12 -> 12
 */
/**
 * get IMEI No
 */
@SuppressLint("MissingPermission")
fun Context.getDeviceId(): String? {
    var deviceId: String? = "android_${UserData.mUserId}"
    try {
        deviceId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        } else {
            val mTelephony = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (mTelephony.subscriberId != null) {
                mTelephony.subscriberId
            } else {
                Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
            }
        }
    } catch (e: Throwable) {
        e.printStackTrace()
    }
    Timber.i("getIMEINo -> $deviceId")
    return deviceId
}

/**
 * get Android OS Version
 */
fun getOSVersion(): String? {
    Timber.i("getOSVersion -> ${Build.VERSION.SDK_INT}")
    return Build.VERSION.SDK_INT.toString() + ""
}

/**
 * open up a date Picker
 */
fun Context.datePicker(txtView: TextView) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
        txtView.text =
            convertToTwoDigitsFormat(dayOfMonth.toString()) + "-" + convertToTwoDigitsFormat((monthOfYear + 1).toString()) + "-" + year
    }, year, month, day)
    dpd.datePicker.maxDate = System.currentTimeMillis()
    dpd.show()
}

/**
 * Mainly Date return a proper format like 02 or 09
 * get two digits number i.e, 1 -> 01 or 12 -> 12
 */
fun convertToTwoDigitsFormat(oneOrTwoDigitData: String?): String {
    return try {
        if (oneOrTwoDigitData!!.length == 1) {
            "0$oneOrTwoDigitData"
        } else
            oneOrTwoDigitData
    } catch (e: Exception) {
        "NA"
    }
}


/**
 * Mainly Date return a proper format like 02 or 09
 *
 * @param hhORmm number e.g. 2, 12
 * @return return proper format e.g. 2-> 02 or 12 ->12
 */
fun convertToTwoDigitsFormat(hhORmm: Int): String {
    var temp = hhORmm.toString() + ""
    try {
        if (temp.length == 1) {
            temp = "0$temp"
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        temp = ""
    }
    return temp
}

/**
 * Get Refreshed Device Token
 */
fun genDeviceToken() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Timber.i("Fetching FCM registration token failed ${task.exception.toString()}")
            return@OnCompleteListener
        }

        // Get new FCM registration token
        mDeviceToken = task.result.toString()

        //Log and toast
        Timber.i("Refreshed device token -> $mDeviceToken")
    })
}

/**
 * get devvice height
 */
fun Activity.getDeviceHeight(): Int {
    val displayMetrics = DisplayMetrics()
    (this).windowManager.defaultDisplay.getMetrics(displayMetrics)
    val deviceWidth: Int = displayMetrics.widthPixels
    val deviceHeight: Int = displayMetrics.heightPixels
    return deviceHeight
}

/**
 * get device width
 */
fun Activity.getDeviceWidth(): Int {
    val displayMetrics = DisplayMetrics()
    (this).windowManager.defaultDisplay.getMetrics(displayMetrics)
    val deviceWidth: Int = displayMetrics.widthPixels
    val deviceHeight: Int = displayMetrics.heightPixels
    return deviceWidth
}

/**
 * run on UI Thread
 */
fun runOnUiThread(runnable: Runnable) {
    if (Looper.myLooper() != Looper.getMainLooper()) {
        Handler(Looper.getMainLooper()).post(runnable)
    } else {
        runnable.run()
    }
}

/**
 *
 */
@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
fun Context.restartApp() {
    val packageManager = this.packageManager
    val intent = packageManager.getLaunchIntentForPackage(this.packageName)
    val componentName = intent!!.component
    val mainIntent = Intent.makeRestartActivityTask(componentName)
    this.startActivity(mainIntent)
    Runtime.getRuntime().exit(0)
}


/**
 * Edit View text change Listener
 */
fun EditText.onTextChanged(onTextChanged: (text: String) -> Unit) {
    val editText: EditText = this
    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //Do Nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(editText.text.toString())
        }

        override fun afterTextChanged(s: Editable?) {
            //Do Nothing
        }
    })
}

/**
 *jetpack navigation back handler
 */
fun Fragment.onBackDispatch(callback: () -> Unit) {
    this.requireActivity().onBackPressedDispatcher.addCallback(this) {
        callback()
    }
}


/**
 * secure Content From Screenshot & Screen Recording
 */
fun Activity.secureContentFromScreenshotAndScreenRecording() {
    if (!BuildConfig.DEBUG /*&& BuildConfig.FLAVOR != BUILD_FLAVOR_DEV*/) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }
}