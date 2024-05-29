package co.si.main.utils.notification.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import co.si.core.utils.runOnUiThread
import co.si.main.R
import co.si.main.landing.MainActivity
import co.si.main.utils.notification.data.FeatureDataPayload
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import corp.hell.kernel.constants.AppData.mPackageName
import corp.hell.kernel.constants.Args.ARG_PARAM1
import corp.hell.kernel.constants.DeviceData
import corp.hell.kernel.utils.fromJsonString
import corp.hell.kernel.utils.toJsonString
import timber.log.Timber


/**
 * Copyright © 2022 Hell Corporation. All rights reserved.
 *
 * @author Harsh Gupta aka Lucifer 😈
 * @since February 05, 2022
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private lateinit var ctx: Context
    private lateinit var notificationManager: NotificationManager
    private val notification_id_default = 3

    private var notificationTitle: String? = null
    private var notificationMessage: String? = null
    private var notificationBitmap: Bitmap? = null

    //    private var pendingIntent: PendingIntent? = null
    private var notificationIdForNotification = notification_id_default
    private var featureScreenValue = NotificationScreen.SPLASH_SCREEN.getValueInLowercase()
    private var featureDataPayload: FeatureDataPayload? = null
    var channelId: String = mChannelIdDefault

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        ctx = this
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        //collecting and setting values from notification Payload
        val notificationBody = remoteMessage.notification
        Timber.d("notification -> notification Body ${toJsonString(notificationBody)}")
        notificationBody?.let { body ->
            notificationTitle = body.title
            notificationMessage = body.body
            channelId = body.channelId ?: mChannelIdDefault
        }

        //collecting and overriding the values if data payload contains these value other wise
        //it'll be notification data payload value
        //Data Message Starts here
        val data = remoteMessage.data
        Timber.d("notification -> data payload ${toJsonString(data)}")
        notificationTitle = data["title"] ?: "Babble Notification"
        notificationMessage = data["message"] ?: ""
        notificationIdForNotification = data["notificationId"]?.toInt() ?: notification_id_default
        channelId = data["channelId"] ?: mChannelIdDefault


        data["imageUrl"]?.let {
            notificationBitmap = NotificationHelper.getBitmapFromUrl(it)
        }
        data["featureScreen"]?.let {
            featureScreenValue = it
            Timber.d("notification -> featureScreenValue is $featureScreenValue")
        }

        data["payload"]?.let {
            Timber.d("notification -> remoteMessage data payload before parse $it")
            val payload = fromJsonString<FeatureDataPayload>(it)
            Timber.d("notification -> remoteMessage data payload after parse $payload")
            if (payload != null) {
                featureDataPayload = payload
                featureDataPayload!!.apply {
                    this.featureScreen = featureScreenValue
                    this.notificationId = notificationIdForNotification
                }
            } else {
                Timber.d("notification -> remoteMessage data payload is null")
            }

        }
        Timber.d(
            "notification -> <<<FINAL>>> remoteMessage featureDataPayload ${
                toJsonString(
                    featureDataPayload
                )
            }"
        )
        //Data Message Ends here

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        createPendingIntentAndStartNotificationProcess()
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     */
    private fun createPendingIntentAndStartNotificationProcess() {
        if (NotificationHelper.isAppOnForeground(applicationContext, mPackageName)) {
            //Foreground
            createAndSendNotification(notificationTitle, notificationMessage)
        } else {
            //Background
            createAndSendNotification(notificationTitle, notificationMessage)
        }
    }

    /**
     * Build a notification Builder and send the notification
     *
     * @param title         notification title
     * @param message       notification message
     */
    private fun createAndSendNotification(
        title: String?, message: String?
    ) {
        try {
//          val uiHandler = Handler(Looper.getMainLooper())
//          uiHandler.post {
            runOnUiThread {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationHelper.createNotificationChannels(channelId)
                        .let { notificationManager.createNotificationChannel(it) }
                }


                val notificationBuilder: NotificationCompat.Builder =
                    NotificationCompat.Builder(this, channelId).apply {
                        this.setSmallIcon(corp.hell.kernel.R.drawable.ic_app_icon)
                            .setContentTitle(title)
                            .setContentText(message).setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true)
                            .setStyle(NotificationCompat.BigTextStyle())
                            .setContentIntent(getPendingIntent())
//                            .setLargeIcon(BitmapFactory.decodeResource(resources, co.si.core.R.drawable.ic_app_icon))
                        if (featureScreenValue != NotificationScreen.CALL_SCREEN.screen) {
                            if (notificationBitmap != null) {
                                this.setLargeIcon(notificationBitmap).setStyle(
                                    NotificationCompat.BigPictureStyle()
                                        .bigPicture(notificationBitmap)
                                    //                                    .bigLargeIcon(null)
                                )
                            }
                        }
                    }

                var soundUri: Uri? =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                if (featureScreenValue == NotificationScreen.CALL_SCREEN.screen) {
                    soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                    notificationBuilder.setSound(soundUri)
                    notificationBuilder.addAction(
                        corp.hell.kernel.R.drawable.ic_app_icon, getActionText(
                            R.string.answer, corp.hell.kernel.R.color.color_green_500
                        ), getPendingIntent(isCallAccepted = true)
                    );
                    notificationBuilder.addAction(
                        corp.hell.kernel.R.drawable.ic_app_icon, getActionText(
                            R.string.decline, corp.hell.kernel.R.color.color_red_500
                        ), getPendingIntent(isCallAccepted = false)
                    );

                } else {
                    if (notificationBitmap != null) {
                        notificationBuilder.setLargeIcon(notificationBitmap).setStyle(
                            NotificationCompat.BigPictureStyle().bigPicture(notificationBitmap)
                            //                                    .bigLargeIcon(null)
                        )
                    }

                }
                notificationBuilder.setSound(soundUri)
                val notification = notificationBuilder.build()
                notificationManager.notify(notificationIdForNotification, notification)
                Timber.i("notification -> create and send notification done !!!")
//              removeNotification(id);
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun getPendingIntent(isCallAccepted: Boolean? = null): PendingIntent {
        val notificationIntent = Intent(this, MainActivity::class.java)

        if (NotificationHelper.isAppOnForeground(applicationContext, mPackageName)) {
            notificationIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        } else {
//            notificationIntent.data = Uri.parse("babble.com://co.si.main/splashFragment")
            notificationIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        var notifyID = notificationIdForNotification
        if (isCallAccepted != null) {
            featureDataPayload.apply {
                this!!.isCallNotificationActionAccepted = isCallAccepted
            }
            notifyID = if (isCallAccepted) 1 else 2
        }
        val bundle = Bundle()
        bundle.putParcelable(ARG_PARAM1, featureDataPayload)
        Timber.d("notification -> MyFirebaseMessagingService bundle: $bundle")
        notificationIntent.putExtras(bundle)

        return PendingIntent.getActivity(
            this,
            notifyID,
            notificationIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    /**
     * Automatically get called if token gets changed
     *
     * @param token auto gen device token
     */
    @SuppressLint("MissingPermission")
    override fun onNewToken(token: String) {
        try {
            DeviceData.mDeviceToken = token
            Timber.i("Refreshed device token -> ${DeviceData.mDeviceToken}")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getActionText(@StringRes stringRes: Int, @ColorRes colorRes: Int): Spannable {
        val spannable: Spannable = SpannableString(applicationContext.getText(stringRes))
        spannable.setSpan(
            ForegroundColorSpan(applicationContext.getColor(colorRes)),
            0,
            spannable.length,
            0
        )
        return spannable
    }
}