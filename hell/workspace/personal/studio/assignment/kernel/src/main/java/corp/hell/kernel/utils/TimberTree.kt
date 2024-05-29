package corp.hell.kernel.utils

import timber.log.Timber

/**
 * Copyright © 2022 Hell Corporation. All rights reserved.
 *
 * @author Harsh Gupta aka Lucifer 😈
 * @since June 20, 2022
 */
class TimberDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return (super.createStackElementTag(element) ?: (" ")).substring(
            0,
            0
        ) + "=> ${element.fileName}:${element.lineNumber}"
    }
}