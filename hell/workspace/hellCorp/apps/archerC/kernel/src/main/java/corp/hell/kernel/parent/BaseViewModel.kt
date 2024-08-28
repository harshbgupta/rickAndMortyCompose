package corp.hell.kernel.parent

import ResponseState
import android.net.Uri
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import corp.hell.kernel.parent.imp.LiveEvent
import corp.hell.kernel.parent.sup.SuperViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : SuperViewModel() {

    val navRequest: LiveEvent<NavDirections> = LiveEvent()
    val deepLinkRequest: LiveEvent<Uri> = LiveEvent()
    val backPressRequest: LiveEvent<Boolean> = LiveEvent()

    var showLoader: () -> Unit = {}
    var hideLoader: () -> Unit = {}

    var showSnackbarSuccess: (message: String?) -> Unit = {}
    var showSnackbarWarning: (message: String?) -> Unit = {}
    var showSnackbarError: (message: String?) -> Unit = {}

    fun navigateToScreen(direction: NavDirections? = null, uri: Uri? = null) =
        viewModelScope.launch(Dispatchers.Main) {
            when {
                direction != null -> navRequest.postValue(direction!!)
                uri != null -> deepLinkRequest.postValue(uri!!)
            }
        }

    fun navigateToPreviousScreen(goBack: Boolean = true) = viewModelScope.launch(Dispatchers.Main) {
        backPressRequest.postValue(goBack)
    }


    fun runOnUiThread(call: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            call.invoke()
        }
    }

    suspend fun <T> callAPIAndPreprocessResponse(
        api: suspend () -> Flow<ResponseState<T>>,
        responseCallBack: suspend (responseState: ResponseState<T>) -> Unit
    ) {
        api.invoke().collect { response ->
            when (response) {
                is ResponseState.ErrorState.NoInternet -> {
                    hideLoader()
//                    navigateToScreen(uri = Uri.parse("verko.com://co.si.main/noInternetFragment"))
                }

                is ResponseState.ErrorState.Unauthorized -> {
                    hideLoader()
//                    navigateToScreen(uri = Uri.parse("verko.com://co.si.main/loginFragment"))
                }
                /**
                 * TODO - Add any other cases here for navigation
                 */
                else -> responseCallBack(response)
            }
        }
    }

}
