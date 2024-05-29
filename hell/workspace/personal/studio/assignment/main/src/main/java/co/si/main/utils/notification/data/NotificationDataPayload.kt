package co.si.main.utils.notification.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

/**
 * Copyright © 2022 Hell Corporation. All rights reserved.
 *
 * @author Harsh Gupta aka Lucifer 😈
 * @since April 05, 2022
 */

data class NotificationDataPayload(
    var title: String? = null,
    var message: String? = null,
    var imageUrl: String? = null,
    var notificationId: Int? = null,
    var featureScreen: String? = null,
    var channelId: String? = null,
    var payload: FeatureDataPayload? = null,
) : Serializable

@Parcelize
data class FeatureDataPayload(
    var featureScreen: String? = null, //not required by backend
    var notificationId: Int? = null, //not required by backend

    // featureScreen == CHAT_SCREEN related
    var roomId: String? = null,
    var name: String? = null,
    var imageUrl: String? = null,
    var chatType: String? = null,

    //featureScreen == R2R_STORY_VIEW Related

    //featureScreen == CALL_SCREEN Related
    var isCallNotificationActionAccepted: Boolean? = null,
    var callStatus: String? = null,
    var clientId: String? = null,
    var callerDetail: UserDetailResVoip? = null,
    var receiversDetail: List<UserDetailResVoip>? = null,
//    var roomId: String, //already declared
    var receiverClientId: String? = null,
    var audioVideoCall: String? = null,
) : Parcelable {
}

data class CallUserResponseAckVoip(
    var callStatus: String?,
    var clientId: String,
    var callerDetail: UserDetailResVoip,
    var receiversDetail: List<UserDetailResVoip>,
    var roomId: String,
    var receiverClientId: String,
    var audioVideoCall: String,
) : Serializable

@Parcelize
data class UserDetailResVoip(
    val id: String,
    val name: String,
    val phone: String,
    val imageUrl: String,
    val about: String,
    val isOnline: Boolean,
    val lastOnlineAt: String,
    val status: String,
    val audioEnabled: Boolean,
    val videoEnabled: Boolean,
) : Parcelable