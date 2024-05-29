package co.si.main.utils.notification.service

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import co.si.main.utils.notification.data.FeatureDataPayload
import com.google.firebase.messaging.FirebaseMessagingService
import corp.hell.kernel.constants.AppData.ctx
import corp.hell.kernel.utils.toJsonString
import timber.log.Timber
import java.net.URL
import java.util.Locale

//channel Ids
const val mChannelIdDefault = "general"
const val mChannelIdChat = "chat"
const val mChannelIdR2R = "r2r"
const val mChannelIdCall = "call"
const val mChannelIdImp = "imp"
const val mChannelIdAds = "ads"

//channel Names
const val mChannelNameDefault = "General"
const val mChannelNameChat = "Chat"
const val mChannelNameR2R = "R2R"
const val mChannelNameCall = "Call"
const val mChannelNameImp = "Important"
const val mChannelNameAds = "Advertisement"

//screen Names
enum class NotificationScreen(val screen: String) {
    SPLASH_SCREEN("splash_screen"),
    CHAT_SCREEN("chat_screen"),
    CALL_SCREEN("call_screen"),
    R2R_SCREEN("r2r_screen"),
    INCOMING_VOICE_CALL("incoming_voice_call"),
    INCOMING_VIDEO_CALL("incoming_video_call"),
    NOTIFICATION_SCREEN("notification_screen");

    /**
     * Protocol name of the event from the api documentation
     *
     * This convert the enum name to lower case of english locale,
     * introduced as the compiler was complaining constants can't be
     * fully lower case.
     **/
    fun getValueInLowercase(): String = this.screen.lowercase(Locale.ENGLISH)
}


val notificationChannels = hashMapOf(
    mChannelIdDefault to mChannelNameDefault,
    mChannelIdChat to mChannelNameChat,
    mChannelIdR2R to mChannelNameR2R,
    mChannelIdCall to mChannelNameCall,
    mChannelIdImp to mChannelNameImp,
    mChannelIdAds to mChannelNameAds
)

object NotificationHelper {


    /**
     * Create notification channel
     *
     * @param ctx Context
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannels(channelId: String?): NotificationChannel {
        val channelIdFinal = channelId ?: mChannelNameDefault
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channelName = notificationChannels[channelIdFinal] ?: mChannelNameDefault
        val notificationChannel = NotificationChannel(channelIdFinal, channelName, importance)
        try {
//                var description = ctx.getString(R.string.notification_channel_desc);
//                notificationChannel.description = description
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return notificationChannel
    }

    /**
     * Check if app is in foreground or in background
     *
     * @param context        Context
     * @param appPackageName package name
     * @return true for Foreground and false if in background
     */
    fun isAppOnForeground(context: Context, appPackageName: String?): Boolean {
        try {
            val activityManager =
                context.getSystemService(FirebaseMessagingService.ACTIVITY_SERVICE) as ActivityManager
            val appProcesses = activityManager.runningAppProcesses ?: return false
            for (appProcess in appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName == appPackageName) {
                    Timber.d("notification -> app is in foreground")
                    return true
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        Timber.d("notification -> app is in background")
        return false
    }


    /**
     * convert image uri to bitmap
     * @param ctx        Context
     * @param imageUri   Uri from notification or data
     *
     * @return bitmap
     */
    fun getBitmapFromUrl(imageUri: String): Bitmap? {
        return try {
            val url = URL(imageUri)
            return BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    fun notificationNavigator(
        payload: FeatureDataPayload,
        findNavController: NavController
    ) {
        var allowCancelNotification = true
        val notifyId = payload.notificationId
        Timber.d(
            "notification -> notificationNavigator data payload ${
                toJsonString(payload)
            }"
        )
        Timber.d("notification -> featureScreen ${payload.featureScreen}")
        if (payload.featureScreen != null) {
            Timber.d("notification -> chat payload ${toJsonString(payload)}")
            when (payload.featureScreen) {
                NotificationScreen.CHAT_SCREEN.screen -> {
                    val uri =
                        Uri.parse("babble.com://co.si.main/chatFragment/${payload.roomId}/${payload.chatType}")
                    findNavController.navigate(uri)
                }

                NotificationScreen.CALL_SCREEN.screen -> {
                    Timber.d("notification -> chat payload ${toJsonString(payload)}")
                    val uri =
                        Uri.parse("babble.com://co.si.main/chatFragment/${payload.roomId}/${payload.chatType}")
                    findNavController.navigate(uri)
                }

                else -> {
                    navigateToSplashFragment(findNavController = findNavController)
                }
            }
        } else {
            Timber.d("notification -> data payload feature screen null")
            navigateToSplashFragment(findNavController = findNavController)
        }
        if (allowCancelNotification) {
            cancelNotification(notifyId)
        }
    }

    private fun cancelNotification(notifyId: Int?) {
        Timber.d("notification -> cancelNotification notificationId $notifyId")
        val ns = Context.NOTIFICATION_SERVICE
        val nMgr = ctx.getSystemService(ns) as NotificationManager
        if (notifyId != null) {
            nMgr.cancel(notifyId)
        } else {
            nMgr.cancelAll()
        }
    }

    private fun navigateToSplashFragment(findNavController: NavController) {
        Timber.d("notification -> navigate to splash fragment")
        val uri = Uri.parse("babble.com://co.si.main/splashFragment")
        findNavController.navigate(uri)
    }

}