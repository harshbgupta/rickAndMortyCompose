package corp.hell.kernel.utils

/**
 * Copyright © 2023 56 AI Technologies. All rights reserved.
 *
 * @author: Harsh Gupta
 * @Date: February 06, 2023
 */
enum class SnackbarState {
    error, success, warning, normal
}

data class SnackbarDTO(
    val state: SnackbarState?, val message: String?
)