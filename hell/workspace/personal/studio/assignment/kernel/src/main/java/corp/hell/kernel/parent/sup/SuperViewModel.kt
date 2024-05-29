package corp.hell.kernel.parent.sup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SuperViewModel @Inject constructor(): ViewModel() {
}
