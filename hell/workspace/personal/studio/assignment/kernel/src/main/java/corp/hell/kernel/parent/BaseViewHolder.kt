package corp.hell.kernel.parent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import corp.hell.kernel.parent.sup.SuperViewHolder

/**
 * Copyright © 2022 Hell Corporation. All rights reserved.
 *
 * @author Harsh Gupta aka Lucifer 😈
 * @since February 18, 2022
 */
open class BaseViewHolder<T>(view: View) : SuperViewHolder(view) {
    open fun bindTo(position: Int, data: T) {
        //over ride this method for implementation
    }
}

inline fun <reified VB : ViewBinding> setBinding(
    parent: ViewGroup, inflate: Inflate<VB>
): VB {
    return inflate.invoke(
        LayoutInflater.from(parent.context), parent, false
    )
}