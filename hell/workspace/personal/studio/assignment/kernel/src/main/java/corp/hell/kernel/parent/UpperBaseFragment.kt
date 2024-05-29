package corp.hell.kernel.parent

import android.os.Bundle
import corp.hell.kernel.parent.sup.SuperFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since August 17, 2021
 */
@AndroidEntryPoint
open class UpperBaseFragment : SuperFragment() {

    /**
     * Getting instance for Current running Fragment
     */
    private var backHandlerInterface: BackHandlerInterface? = null

    /**
     * For Handling back Button & Most imp in every Fragment when u r overriding it is better to put whole implementation of
     * this method should be try catch for better use.
     *
     * @return return true if your Fragment is Handling back button, false if u r letting Activity handle back buttton
     * if u r handling in fragment(means if u r returning true) den in this condition dont let ur activity handle is this back
     * only nad only activity will handle back when ur fragment is not handling(means if u r rretuning false)
     *
     *
     * e.g. see onBackPressed of Activity and Fragments
     */
    open fun onBackPressed(): Boolean {
        Timber.i("Base Fragment on Back Pressed")
        return false
    }

    /**
     * Lifecycle Method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backHandlerInterface = if (requireActivity() !is BackHandlerInterface) {
            throw ClassCastException("Hosting activity must implement BackHandlerInterface")
        } else {
            requireActivity() as BackHandlerInterface?
        }
        checkAppVersion()
    }

    /**
     * Lifecycle Method
     */
    override fun onStart() {
        super.onStart()
        // Mark this fragment as the selected Fragment.
        backHandlerInterface!!.setSelectedFragment(this)
    }

    /**
     * Lifecycle method
     */
    override fun onResume() {
        super.onResume()
        checkAppVersion()
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
     * Interface: provide current Fragment instance
     */
    open interface BackHandlerInterface {
        fun setSelectedFragment(pBackHandledFragment: UpperBaseFragment)
    }
}