package corp.hell.kernel.parent.imp

import androidx.lifecycle.ViewModel
import corp.hell.kernel.utils.SnackbarDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * This is the viewmodel for the main activity.
 * This can be used to share data between the fragments and the main activity.
 * Right now, the progress bar in the main activity shall be used across all the fragments whenever a loader is required
 */
@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    val showProgress : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showSnackbar: LiveEvent<SnackbarDTO> = LiveEvent()
}