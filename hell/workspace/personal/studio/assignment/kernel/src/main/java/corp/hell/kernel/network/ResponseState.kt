import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException

interface ErrorData<T> {
    val throwable: Throwable?
    val message: String
}

sealed class ResponseState<T> {
    class LoadingState<T> : ResponseState<T>()

    data class SuccessState<T>(
        val res: T
    ) : ResponseState<T>()

    sealed class ErrorState<T> : ErrorData<T>, ResponseState<T>() {
        data class IO<T>(
            override val throwable: Exception,
            override val message: String
        ) : ErrorState<T>() {
            constructor(exp: Exception) : this(
                exp,
                exp.message ?: GENERAL_ERROR_MESSAGE
            )
        }

        data class SocketTimeout<T>(
            override val throwable: Exception,
            override val message: String
        ) : ErrorState<T>()

        data class Http<T>(
            override val throwable: HttpException,
            override val message: String
        ) : ErrorState<T>() {
            constructor(exp: HttpException) : this(
                exp,
                exp.message ?: GENERAL_ERROR_MESSAGE
            )
        }

        data class Undefined<T>(
            override val throwable: Throwable? = null,
            override val message: String
        ) : ErrorState<T>() {
            constructor(throwable: Throwable) : this(
                throwable,
                throwable.message ?: GENERAL_ERROR_MESSAGE
            )
        }

        data class UnknownError<T>(
            override val message: String,
            override val throwable: Throwable? = null,
        ) : ErrorState<T>() {
            constructor(throwable: Throwable) : this(
                throwable.message ?: GENERAL_ERROR_MESSAGE,
                throwable
            )
        }

        /**
         * TODO define more cases for NoInternet if required and handle accordingly
         */
        data class NoInternet<T>(
            override val throwable: Throwable? = null,
            override val message: String = GENERAL_NO_INTERNET_MESSAGE
        ) : ErrorState<T>()

        data class InternalServerError<T>(
            override val message: String = INTERNAL_SERVER_ERROR,
            override val throwable: Throwable? = null
        ) : ErrorState<T>()

        data class NotFound<T>(
            override val message: String = NOT_DATA_FOUND,
            override val throwable: Throwable? = null
        ) : ErrorState<T>()

        data class MissingData<T>(
            override val message: String = DATA_MISSING,
            override val throwable: Throwable? = null
        ) : ErrorState<T>()

        data class BadRequest<T>(
            override val message: String = BAD_REQUEST,
            override val throwable: Throwable? = null
        ) : ErrorState<T>()

        data class Unauthorized<T>(
            override val message: String = UNAUTHORIZED,
            override val throwable: Throwable? = null
        ) : ErrorState<T>()
    }
}

fun <T, R> Flow<ResponseState<T>>.transformRequestStateType(
    transform: (T) -> R
): Flow<ResponseState<R>> = map { responseState ->
    when (responseState) {
        is ResponseState.SuccessState<T> -> ResponseState.SuccessState<R>(transform(responseState.res))
        else -> responseState as ResponseState<R>

    }
}

const val GENERAL_ERROR_MESSAGE = "Error"
const val GENERAL_NO_INTERNET_MESSAGE = "Internet/server not reachable"
const val INTERNAL_SERVER_ERROR = "Server error"
const val NOT_DATA_FOUND = "Not data found"
const val DATA_MISSING = "Data missing"
const val BAD_REQUEST = "Bad request"
const val UNAUTHORIZED = "Unauthorized Request"
const val PAYMENT_REQUIRED = "Payment required"