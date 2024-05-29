package co.si.main.splash.ui

import android.os.Handler
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.si.core.utils.closeKeyboard
import co.si.main.R
import co.si.main.databinding.SplashFragmentBinding
import co.si.main.splash.networking.SplashViewModel
import co.si.main.utils.getPreBasicDataFromDataStoreAndEstablishSocketConnection
import com.bumptech.glide.Glide
import corp.hell.kernel.parent.BaseFragment
import corp.hell.kernel.constants.AppData.mLanguage
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale

class SplashFragment :
    BaseFragment<SplashFragmentBinding, SplashViewModel>(SplashFragmentBinding::inflate) {

    override val viewModel by viewModels<SplashViewModel>()
    private val splashTimeMillis = 2000L

    override fun onPostCreateView() {
        super.onPostCreateView()
        restOfCoding()
    }


    /**
     * Rest of Coding starts here
     */
    private fun restOfCoding() = lifecycleScope.launch {
        getPreBasicDataFromDataStoreAndEstablishSocketConnection()
        closeKeyboard(binding.root)
        Glide.with(requireContext()).load(corp.hell.kernel.R.drawable.ic_app_icon)
            .into(binding.imageView2)
        setLanguage()
        liveDataObserver()//observing live data
        Handler().postDelayed({ postSplash() }, splashTimeMillis)
    }


    /**
     * Put live data observer here
     */
    private fun liveDataObserver() {

    }

    /**
     * get data from sharedPreference/Data store
     */
//    private suspend fun getPreData() {
//        mAuthToken = DataStorePref.readFromDataStore(DataStorePref.AUTH_TOKEN)
//        mMobileNumber = DataStorePref.readFromDataStore(DataStorePref.PHONE_NUMBER)
//        mLanguage = DataStorePref.readFromDataStore(DataStorePref.LANGUAGE) ?: "en"
//        mUserId = DataStorePref.readFromDataStore(DataStorePref.USER_ID)
//        mProfilePic = DataStorePref.readFromDataStore(DataStorePref.PROFILE_URL)
//        mFullName = DataStorePref.readFromDataStore(DataStorePref.FULL_NAME)
//        mCountryCode = DataStorePref.readFromDataStore(DataStorePref.DIAL_CODE)
//        mPhoneNumberWithoutDialCode =
//            DataStorePref.readFromDataStore(DataStorePref.PHONE_NUMBER_WITHOUT_DIAL_CODE)
//
//        Timber.d("data -> splash authToken $mAuthToken")
//        Timber.d("data -> splash mobileNumber $mMobileNumber")
//        Timber.d("data -> splash language $mLanguage")
//        Timber.d("data -> splash userId $mUserId")
//        Timber.d("data -> splash profilePicUrl $mProfilePic")
//        Timber.d("data -> splash full name $mFullName")
//    }

    /**
     * Setting language
     */
    private fun setLanguage() {
        var myLocale = Locale(mLanguage)
        var dm = resources.displayMetrics
        var conf = resources.configuration
        conf.locale = myLocale
        resources.updateConfiguration(conf, dm)
    }

    /**
     * Code after handler
     */
    private fun postSplash() {
        try {
            if (true/*dataPayload == null*/) {
                // Normal Flow
                callLoginFragment()
            } else {
                //through Notification
                callLoginFragment()
            }
        } catch (e: Exception) {
            Timber.d("Exception: ${e.printStackTrace()}")
        }
    }

    private fun callLoginFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }
}