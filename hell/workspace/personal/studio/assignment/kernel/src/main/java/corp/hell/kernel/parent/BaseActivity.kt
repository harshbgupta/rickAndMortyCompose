package corp.hell.kernel.parent

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import corp.hell.kernel.BuildConfig
import corp.hell.kernel.parent.sup.SuperActivity
import timber.log.Timber


/**
 * Copyright © 2021 Hell Corporation. All rights reserved.
 *
 * @author Mr. Lucifer
 * @since August 17, 2021
 */
open class BaseActivity : SuperActivity(), UpperBaseFragment.BackHandlerInterface {

    private var selectedFragment: UpperBaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        checkAppVersion()
    }

    override fun setSelectedFragment(pBackHandledFragment: UpperBaseFragment) {
        this.selectedFragment = pBackHandledFragment
    }

    /**
     * Lifecycle method
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (selectedFragment != null && selectedFragment!!.onBackPressed()) {
            //Don't do anything Here. Fragment is Handling back
            Timber.i("onBackPressed method -> Fragment is Handling ...")
        } else {
            //Fragment is not Handling Back. Activity is handling here
            Timber.i("onBackPressed method -> Handled by Activity ...")
            super.onBackPressed()
        }
    }

    /**
     * Lifecycle method
     */
    override fun onResume() {
        super.onResume()
        checkAppVersion()
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause Socket")
    }

    /**
     * checks app version (important Method: do not change anything in this method. If deleted, may cause major issue in future)
     */
    private fun checkAppVersion() {
        if (BuildConfig.DEBUG) {
            return
        }
        try {
//            mVersionName?.let { versionChecker(it) }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}