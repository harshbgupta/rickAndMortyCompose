package corp.hell.kernel.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.util.Base64
import timber.log.Timber
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class AppSignatureHelperDev(context: Context?) :
    ContextWrapper(context) {// Get all package signatures for the current package

    // For each signature create a compatible hash
    /**
     * ******************* this is valid only for dev ***********************
     *
     * **** for play store app get SHA256 Certificate from google console and
     * generate thee HASH Key provided by Google Commands *******************
     *
     * call this ("getAppSignatures") method to get 11 character HASH key for
     * SMS Retriever API to put in SMS template
     *
     * get result by printing (as Json in string) the return ArrayList.
     *
     * Get all the app signatures for the current package
     * @return
     */
    val appSignatures: ArrayList<String>
        get() {
            val appCodes = ArrayList<String>()
            try {
                // Get all package signatures for the current package
                val packageName = packageName
                val packageManager = packageManager
                val signatures = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                ).signatures

                // For each signature create a compatible hash
                for (signature in signatures) {
                    val hash = hash(packageName, signature.toCharsString())
                    if (hash != null) {
                        appCodes.add(String.format("%s", hash))
                    }
                }
            } catch (e: PackageManager.NameNotFoundException) {
                Timber.e("Unable to find package to obtain hash.")
                e.printStackTrace()
            }
            return appCodes
        }

    companion object {
        private const val HASH_TYPE = "SHA-256"
        const val NUM_HASHED_BYTES = 9
        const val NUM_BASE64_CHAR = 11
        private fun hash(packageName: String, signature: String): String? {
            val appInfo = "$packageName $signature"
            try {
                val messageDigest = MessageDigest.getInstance(HASH_TYPE)
                messageDigest.update(appInfo.toByteArray(StandardCharsets.UTF_8))
                var hashSignature = messageDigest.digest()

                // truncated into NUM_HASHED_BYTES
                hashSignature = Arrays.copyOfRange(hashSignature, 0, NUM_HASHED_BYTES)
                // encode into Base64
                var base64Hash =
                    Base64.encodeToString(hashSignature, Base64.NO_PADDING or Base64.NO_WRAP)
                base64Hash = base64Hash.substring(0, NUM_BASE64_CHAR)
                Timber.i(
                    String.format(
                        "SMS Retriever SMS CODE pkg: %s -- hash: %s",
                        packageName,
                        base64Hash
                    )
                )
                return base64Hash
            } catch (e: NoSuchAlgorithmException) {
                Timber.e("hash:NoSuchAlgorithm")
                e.printStackTrace()
            }
            return null
        }
    }
}