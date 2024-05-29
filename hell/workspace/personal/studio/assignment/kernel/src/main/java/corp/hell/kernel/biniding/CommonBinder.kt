package corp.hell.kernel.biniding

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import timber.log.Timber
import java.lang.NullPointerException


/**
 * make an view enable or disable
 */
@BindingAdapter("enableStatus")
fun View.enableStatus(status: Boolean?) {
    if (status == null) {
        Timber.e("status value is null")
        return
    }
    isClickable = status
    isEnabled = status
}

/**
 * Set visibility on and off
 */
@BindingAdapter("isVisible")
fun View.isVisible(visible: Boolean?) {
    if (visible == null) {
        Timber.e("visible value is null")
        return
    }
    this.visibility = if (visible == true) View.VISIBLE else View.GONE
}

/**
 * Set visibility on and invisible
 */
@BindingAdapter("isInvisible")
fun View.isInvisible(isInvisible: Boolean?) {
    if (isInvisible == null) {
        Timber.e("isInvisible value is null")
        return
    }
    this.visibility = if (isInvisible == true) View.INVISIBLE else View.VISIBLE
}


/**
 * Setting Text color from hex-value,
 * or passing image id*/
@BindingAdapter("textColorInHex")
fun TextView.setTextColorInHex(textColor: String?) {
    if (textColor == null) {
        Timber.e("textColor value is null")
        return
    }
    try {
        this.setTextColor(Color.parseColor(textColor))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@SuppressLint("ClickableViewAccessibility")
@BindingAdapter("setScrollingEnabled")
fun EditText.setScrollingEnabled(enable: Boolean?) {
    if (enable == null) {
        Timber.e("enable value is null")
        return
    }
    setOnTouchListener { v, event ->
        v.parent.requestDisallowInterceptTouchEvent(true)
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
        }
        false
    }
}
