package com.hell.rickandmorty.ui.splash

import androidx.lifecycle.ViewModel
import com.hell.rickandmorty.data.database.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val sharedPref: SharedPref) : ViewModel() {
    var isFirstOpen: Boolean
        get() = sharedPref.isFirstOpen
        set(value) {
            sharedPref.isFirstOpen = value
        }
}