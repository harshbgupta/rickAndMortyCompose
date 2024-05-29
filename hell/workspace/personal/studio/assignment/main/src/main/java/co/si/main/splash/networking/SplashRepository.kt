package co.si.main.splash.networking

import javax.inject.Inject

/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since September 08, 2021
 */
class SplashRepository @Inject constructor(private val service: SplashService) {

    fun getAWS(userId: Long) = service.getAWS(userId)

    fun getRoleDetails(userId: Long) = service.getRoleDetails(userId)

}