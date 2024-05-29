package co.si.main.landing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import co.si.core.utils.genDeviceToken
import co.si.main.R
import co.si.main.databinding.MainActivityBinding
import coil.load
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import corp.hell.kernel.parent.BaseActivity
import corp.hell.kernel.parent.imp.MainViewModel
import corp.hell.kernel.constants.AppData.ctx
import corp.hell.kernel.constants.OtherData.showCrashPopUp
import corp.hell.kernel.databinding.CustomSnackbarViewBinding
import corp.hell.kernel.uiComponents.DialogFactory
import corp.hell.kernel.utils.SnackbarState
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var navView: BottomNavigationView
    private lateinit var mainViewModel: MainViewModel

    /**
     * Lifecycle Method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showCrashPopUpListener()
        setClickListener()
        restOfCoding()
    }

    /**
     * Put all click listener
     */
    private fun setClickListener() {}

    /**
     * Rest of Coding
     */
    private fun restOfCoding() {
        genDeviceToken()
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        commonSetUp()
        //set Bottom Navigation
        navView = binding.navView
        val navController = findNavController(R.id.navHostFragmentActivityLanding)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            destinationChanger(destination)
        }
    }

    private fun commonSetUp() {
        loaderSetUp()
        snackBarSetUp()
    }

    private fun destinationChanger(destination: NavDestination) {
        when (destination.id) {
            R.id.loginFragment -> navView.visibility =
                View.VISIBLE

            else -> navView.visibility = View.GONE
        }
    }

    private fun snackBarSetUp() {
        if (this::mainViewModel.isInitialized) {
            Timber.d("snackBarSetUp, mainViewModel is Initialized")
            mainViewModel.showSnackbar.observe(this) { dto ->
                showCustomSnackBar(dto.state, dto.message)
            }
        } else {
            Timber.d("snackBarSetUp, mainViewModel is not Initialized")
        }
    }

    private fun loaderSetUp() {
        if (this::mainViewModel.isInitialized) {
            Timber.d("loaderInitialization, mainViewModel is Initialized")
            binding.viewModelMain = mainViewModel
        } else {
            Timber.d("loaderInitialization, mainViewModel is not Initialized")
        }
    }

    private fun showCustomSnackBar(
        state: SnackbarState?,
        message: String?,
        duration: Int = 10,
    ) {
        message ?: return
        state ?: SnackbarState.normal
        var snackbar: Snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, duration * 1000)

//        val custom: View =
//            LayoutInflater.from(this).inflate(
//                R.layout.custom_snackbar_view,
//                null
//            )

        Timber.d("snackBarSetUp juts before")
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val bindingSnackbar = CustomSnackbarViewBinding.inflate(inflater)
        Timber.d("snackBarSetUp juts after")
        (snackbar.view as ViewGroup).removeAllViews()
        (snackbar.view as ViewGroup).addView(bindingSnackbar.root)
        snackbar.view.setBackgroundColor(
            ContextCompat.getColor(
                ctx, android.R.color.transparent
            )
        )
        val clRoot = bindingSnackbar.clRoot
        val imgClose = bindingSnackbar.ivClose
        val txtMessage = bindingSnackbar.tvMessage
        val imgState = bindingSnackbar.ivIcon
        imgState.visibility = View.VISIBLE
        txtMessage.setTextColor(
            ContextCompat.getColor(
                ctx, corp.hell.kernel.R.color.color_black_1000
            )
        )
        imgClose.setColorFilter(
            ContextCompat.getColor(
                ctx, corp.hell.kernel.R.color.color_black_1000
            )
        )
        when (state) {
            SnackbarState.success -> {
                Timber.d("snackBarSetUp success")
                imgState.load(corp.hell.kernel.R.drawable.ic_success)
                clRoot.background = ContextCompat.getDrawable(
                    ctx, corp.hell.kernel.R.drawable.snackbar_background_success
                )
            }

            SnackbarState.warning -> {
                Timber.d("snackBarSetUp warning")
                imgState.load(corp.hell.kernel.R.drawable.ic_warning)
                clRoot.background = ContextCompat.getDrawable(
                    ctx, corp.hell.kernel.R.drawable.snackbar_background_warning
                )
            }

            SnackbarState.error -> {
                Timber.d("snackBarSetUp error")
                imgState.load(corp.hell.kernel.R.drawable.ic_error)
                clRoot.background = ContextCompat.getDrawable(
                    ctx, corp.hell.kernel.R.drawable.snackbar_background_error
                )
            }

            else -> {
                Timber.d("snackBarSetUp else")
                imgState.visibility = View.GONE
                txtMessage.setTextColor(
                    ContextCompat.getColor(
                        ctx, corp.hell.kernel.R.color.color_white_1000
                    )
                )
                imgClose.setColorFilter(
                    ContextCompat.getColor(
                        ctx, corp.hell.kernel.R.color.color_white_1000
                    )
                )
                clRoot.background = ContextCompat.getDrawable(
                    ctx, corp.hell.kernel.R.drawable.snackbar_background_normal
                )
            }
        }
        txtMessage.text = message
        imgClose.setOnClickListener {
            snackbar.dismiss()
        }
        snackbar.setAnchorView(R.id.navView)
        snackbar.show()
    }

    /**
     * Popup to show app crash
     * It will be recorded as Non fatal issue in crashlytics
     */
    private fun showCrashPopUpListener() {
        showCrashPopUp.observe(this) {
            DialogFactory.showCrashCustomAlertDialog(this,
                positiveClickAction = { launchMainActivity() },
                negativeClickAction = { launchMainActivity() })
        }
    }

    private fun launchMainActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}