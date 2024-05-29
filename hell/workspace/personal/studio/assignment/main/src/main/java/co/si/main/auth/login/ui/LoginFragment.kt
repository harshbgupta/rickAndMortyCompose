package co.si.main.auth.login.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import co.si.core.utils.*
import co.si.main.R
import co.si.main.auth.login.networking.LoginViewModel
import co.si.main.databinding.LoginFragmentBinding
import corp.hell.kernel.parent.BaseFragment
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment :
    BaseFragment<LoginFragmentBinding, LoginViewModel>(LoginFragmentBinding::inflate),
    View.OnClickListener {

    override val viewModel by viewModels<LoginViewModel>()
    private val privacyUrl = "https://homehub.global/privacy-policy-Brickez.html"
    private var mLanguage: String? = null
    private var phoneNumber: String? = null

    /**
     * Life Cycle Method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mLanguage = it.getString("language")
        }
    }

    /**
     * Life Cycle Method
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Life Cycle Method
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        dataObserver()
        restOfCoding()
    }

    private fun dataObserver() {
    }

    /**
     * Set all click listener here
     */
    private fun setClickListener() {
        binding.root.setOnClickListener(this)
        binding.button.setOnClickListener(this)
        binding.terms.setOnClickListener(this)
    }

    /**
     * Called when a view has been clicked.
     */
    override fun onClick(v: View) {
        closeKeyboard(binding.root)
        when (v.id) {
            R.id.button -> {
                if (binding.emailPasswordLayout.visibility == View.VISIBLE) {
                    val email = binding.email.text.toString()
                    val password = binding.password.text.toString()
                    if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                        viewModel.callPasswordVerifyAPI(email, password)
                    } else {
                        viewModel.showSnackbarError(getString(R.string.email_password_not_entered))
                    }
                } else {
                    val phone = binding.phone.text.toString()
                    if (!phone.isNullOrEmpty() and (phone.length == 10)) {
                        viewModel.showSnackbarSuccess("Login Successful")
                        //TODO: call check user exist API and put following navigation line in live data observer
                        findNavController().navigate(R.id.action_loginFragment_to_otpFragment)
                    } else {
                        viewModel.showSnackbarError(getString(R.string.valid_phone_warning))
                    }
                }
            }

            R.id.terms -> {
                //Just checking crash Handler
                /*val crash: String? = null
                crash!!.toString()*/

                try {
                    viewModel.showSnackbarError("$privacyUrl")
                    val httpIntent = Intent(Intent.ACTION_VIEW)
                    httpIntent.data = Uri.parse(privacyUrl)
                    startActivity(httpIntent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    try {
                        val httpIntent = Intent(Intent.ACTION_VIEW)
                        httpIntent.data = Uri.parse("https://$privacyUrl")
                        startActivity(httpIntent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            R.id.root -> {
                closeKeyboard(binding.root)
            }
        }
    }

    /**
     * Rest of Coding
     */
    private fun restOfCoding() {
        viewModel.init()
    }

    private fun setNumber(number: String) {
        Timber.e("requestHint setNumber number$number")
        if (!number.isNullOrBlank() && number.length >= 10) {
            phoneNumber = number.substring(number.length - 10, number.length)
            Timber.e("requestHint setNumber phoneNumber$phoneNumber")
            binding.phone.setText(phoneNumber)
        } else {
            Timber.e("requestHint Phone Number Invalid $number")
        }
    }
}