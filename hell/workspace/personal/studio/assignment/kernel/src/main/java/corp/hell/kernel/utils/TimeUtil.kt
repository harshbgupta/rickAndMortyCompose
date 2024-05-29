package corp.hell.kernel.utils

import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun timeFormat(dataDate: String?): String? {
    var convTime: String? = null
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val pasTime = dateFormat.parse(dataDate)
        val nowTime = Date()
        val dateDiff = nowTime.time - pasTime.time
        convTime = format1(dateDiff)
//        convTime = format2(dateDiff)
    } catch (e: ParseException) {
        e.printStackTrace()
        Timber.e("ConvTimeE:" + e.message)
    }
    return convTime
}

fun format1(dateDiff: Long?):String? {
    if (dateDiff == null)
        return null

    var convTime: String? = null
    val prefix = ""
    val suffix = "Ago"
    val second = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
    val minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
    val hour = TimeUnit.MILLISECONDS.toHours(dateDiff)
    val day = TimeUnit.MILLISECONDS.toDays(dateDiff)
    if (second < 60) {
        convTime = "$second Seconds $suffix"
    } else if (minute < 60) {
        convTime = "$minute Minutes $suffix"
    } else if (hour < 24) {
        convTime = "$hour Hours $suffix"
    } else if (day >= 7) {
        convTime = if (day > 360) {
            (day / 360).toString() + " Years " + suffix
        } else if (day > 30) {
            (day / 30).toString() + " Months " + suffix
        } else {
            (day / 7).toString() + " Week " + suffix
        }
    } else if (day < 7) {
        convTime = "$day Days $suffix"
    }
    return convTime
}

fun format2(dateDiff: Long?): String? {
    if (dateDiff == null)
        return null
    var convTime: String? = null
    val prefix = ""
    val suffix = ""
    try {
        val second = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
        val minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
        val hour = TimeUnit.MILLISECONDS.toHours(dateDiff)
        val day = TimeUnit.MILLISECONDS.toDays(dateDiff)
        convTime = if (second < 60) {
            second.toString() + "sec " + suffix
        } else if (minute < 60) {
            minute.toString() + "min " + suffix
        } else if (hour < 24) {
            hour.toString() + "h " + suffix
        } else { /*if (day ?= 7) {*/
            day.toString() + "d " + suffix
        } /*else if (day > 7) {
                convTime = pasTimeInString;
            }*/
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return convTime
}