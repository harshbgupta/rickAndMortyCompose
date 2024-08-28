package corp.hell.kernel.uiComponents

import android.widget.Toast
import androidx.annotation.StringRes
import co.si.core.utils.runOnUiThread
import corp.hell.kernel.constants.AppData.ctx
import javax.inject.Singleton

@Singleton
object ToastUI {

    private var toast: Toast? = null
    private fun show(length: Int, @StringRes resId: Int, vararg extras: String) {
        runOnUiThread {
            show(length, ctx.getString(resId, *extras))
        }
    }

    private fun show(length: Int, message: String?) {
        runOnUiThread {
            if (toast != null) {
                toast!!.cancel()
            }
            toast = Toast.makeText(ctx, message, length)
            toast!!.show()
        }
    }

    fun sToast(message: String) = show(Toast.LENGTH_SHORT, message)
    fun sToast(@StringRes message: Int) = show(Toast.LENGTH_SHORT, message)

    fun lToast(message: String) = show(Toast.LENGTH_LONG, message)
    fun lToast(@StringRes resId: Int, vararg extras: String) =
        show(Toast.LENGTH_LONG, resId, *extras)
}
