package corp.hell.kernel.parent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import corp.hell.kernel.parent.imp.MainViewModel
import corp.hell.kernel.utils.SnackbarDTO
import corp.hell.kernel.utils.SnackbarState
import kotlinx.coroutines.launch
import timber.log.Timber

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

/**
 * Inherit from this class with other UI Fragments by passing the fragments viewmodel and the binding variable
 * This shall take care of inflating and setting up the link between  main view model for common progress bar
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(private val inflate: Inflate<VB>) :
    UpperBaseFragment() {

    lateinit var binding: VB
    protected abstract val viewModel: VM
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        commonSetUp()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate.invoke(inflater, container, false)
        if (binding is ViewDataBinding)
            (binding as ViewDataBinding).lifecycleOwner = viewLifecycleOwner
        subscribeNavRequest()
        onPostCreateView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPostViewCreated(savedInstanceState)
    }

    /**
     * Lifecycle method
     */
    override fun onResume() {
        super.onResume()
        checkAppVersion()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Other Methods
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Override this function in the derived fragment to do restOfCoding job
     */
    protected open fun onPostCreateView() {}

    /**
     * Override this function in the derived fragment to do restOfCoding job
     */
    protected open fun onPostViewCreated(savedInstanceState: Bundle?) {}

    /**
     * Handling navigation
     */
    private fun subscribeNavRequest() = lifecycleScope.launch {
        viewModel.navRequest.observe(viewLifecycleOwner) { request: NavDirections ->

            try {
                findNavController().navigate(request)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModel.deepLinkRequest.observe(viewLifecycleOwner) { uri ->
            try {
                findNavController().navigate(uri)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModel.backPressRequest.observe(viewLifecycleOwner) { goback ->
            if (goback) {
                try {
                    findNavController().popBackStack()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * checks app version (important Method: do not change anything in this method. If deleted, may cause major issue in future)
     */
    private fun checkAppVersion() {
        try {
//            mVersionName?.let { requireContext().versionChecker(it) }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * Some basic common setup
     */
    private fun commonSetUp() {
        loaderSetUp()
        snackBarSetUp()
    }

    /**
     * Loader set up
     */
    private fun loaderSetUp() {
        //loader setup
        viewModel.showLoader = {
            mainViewModel.showProgress.tryEmit(true)
        }
        viewModel.hideLoader = {
            mainViewModel.showProgress.tryEmit(false)
        }
    }


    /**
     * Snack bar set up
     */
    private fun snackBarSetUp() {
        //snackBar setup
        viewModel.showSnackbarSuccess = { message ->
            Timber.d("showSnackbar Success, message $message")
            mainViewModel.showSnackbar.postValue(SnackbarDTO(SnackbarState.success, message))
        }
        viewModel.showSnackbarWarning = { message ->
            Timber.d("showSnackbar Warning, message $message")
            mainViewModel.showSnackbar.postValue(SnackbarDTO(SnackbarState.warning, message))
        }
        viewModel.showSnackbarError = { message ->
            Timber.d("showSnackbar Error, message $message")
            mainViewModel.showSnackbar.postValue(SnackbarDTO(SnackbarState.error, message))
        }
    }
}