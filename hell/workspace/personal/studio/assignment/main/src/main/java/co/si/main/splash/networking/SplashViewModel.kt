package co.si.main.splash.networking

import corp.hell.kernel.parent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since September 08, 2021
 */
@HiltViewModel
class SplashViewModel @Inject constructor(private val repo: SplashRepository) : BaseViewModel() {

}