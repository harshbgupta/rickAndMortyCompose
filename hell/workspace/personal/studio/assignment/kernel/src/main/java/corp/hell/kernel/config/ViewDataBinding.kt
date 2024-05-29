package corp.hell.kernel.config

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> inflateWithParent(
    parent: ViewGroup,
    @LayoutRes resourceId: Int
): T {
    return DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        resourceId,
        parent,
        false,
        DataBindingUtil.getDefaultComponent()
    )
}