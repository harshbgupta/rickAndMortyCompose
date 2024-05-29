package corp.hell.kernel.network

import BAD_REQUEST
import DATA_MISSING
import GENERAL_NO_INTERNET_MESSAGE
import INTERNAL_SERVER_ERROR
import NOT_DATA_FOUND
import PAYMENT_REQUIRED
import ResponseState
import UNAUTHORIZED
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import corp.hell.kernel.BuildConfig
import corp.hell.kernel.constants.AppData.BUILD_FLAVOR_DEV
import corp.hell.kernel.utils.toJsonString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject


/**
 * Base Repository for manging the network calls
 */
abstract class NetworkRepository {
    @Inject
    lateinit var context: Context

    companion object {
        const val NO_MESSAGE_ERROR = "No Message, code: %s"
        const val NO_CODE_ERROR = "No Code, message: %s"
        const val SERVER_ERROR_ERROR_MSG = "Server error, message: %s"
        const val UNKNOWN_SERVER_ERROR = "Unknown server error, message: %s"
    }

    inline fun <reified T> requestAPIFlow(
        mockApiEnabled: Boolean = false,
        jsonFilePath: String? = "",
        crossinline request: suspend () -> Response<T>
    ): Flow<ResponseState<T>> = flow {
        when (true/*isInternetAvailable()*/) {
            true -> {
                try {
                    // Internet or Server are up
                    emit(ResponseState.LoadingState<T>())
                    val result = tryApiRequest(mockApiEnabled, jsonFilePath ?: "", request)
                    Timber.e("api => requestAPIFlow result ${toJsonString(result)}")
                    emit(result)
                } catch (e: Exception) {
                    Timber.e("api => requestAPIFlow catch $e")
                    e.printStackTrace()
                    emit(
                        ResponseState.ErrorState.UnknownError(e)
                    )
                }
            }

            false -> {
                //if internet or server is not reachable
                Timber.e("api => requestAPIFlow No internet or Server")
                emit(ResponseState.ErrorState.NoInternet(message = GENERAL_NO_INTERNET_MESSAGE))
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend inline fun <reified T> tryApiRequest(
        mockApiEnabled: Boolean,
        jsonFilePath: String?,
        crossinline request: suspend () -> Response<T>
    ): ResponseState<T> {
        return try {
            val res: Response<T> =
                if (BuildConfig.DEBUG && BuildConfig.FLAVOR == BUILD_FLAVOR_DEV && mockApiEnabled) {
                    Timber.d("api => Mock API from client")
                    mockApi(jsonFilePath)
                } else {
                    Timber.d("api => Network Call")
                    request()
                }
            val statusCode = res.code().toString()
            val messgae = res.message()

            if (res.isSuccessful) {
                Timber.d("api => tryApiRequest res is successful")
                val baseResponse: T? = res.body()
                Timber.d("api => tryApiRequest baseResponse ${toJsonString(baseResponse)}")
                if (T::class == BaseResponse::class) {
                    val resBase = baseResponse as BaseResponse<*>

                    /*
                    following is making hardcoding success state, in case there is another
                    way to check success response, then check that condition if true hardcode
                    the success code as following
                    */
//                    if (resBase.code == null) resBase.apply { code = "200" }
                    resBase.responseCheck(
                        resBase.code, resBase.message ?: NO_MESSAGE_ERROR.format(
                            statusCode
                        )
                    )
                } else {
                    Timber.e("api => tryApiRequest Response parsing error")
                    ResponseState.ErrorState.BadRequest("Response parsing error")
                }
            } else {
                Timber.d("api => tryApiRequest res is not successful")
                val type = object : TypeToken<ErrorResp>() {}.type
                var errorResp: ErrorResp? = Gson().fromJson(res.errorBody()!!.charStream(), type)
                Timber.d("api => tryApiRequest errorResp ${toJsonString(errorResp)}")
                handleErrors(statusCode, errorResp?.message)
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is HttpException -> {
                    Timber.e("api => tryApiRequest HttpException code: ${throwable.code()}, message:${throwable.message()}")
                    handleErrors(
                        throwable.code().toString(), SERVER_ERROR_ERROR_MSG.format(
                            throwable.message().toString()
                        )
                    )
                }

                is SocketTimeoutException -> {
                    Timber.d("api => tryApiRequest SocketTimeoutException exception: $throwable")
                    ResponseState.ErrorState.SocketTimeout<T>(
                        throwable, message = "Time out Exception"
                    )
                }

                is IOException -> {
                    Timber.d("api => tryApiRequest IOException exception: $throwable")
                    ResponseState.ErrorState.IO<T>(throwable)
                }

                else -> {
                    Timber.d("api => tryApiRequest Unknown exception: $throwable ")
                    ResponseState.ErrorState.UnknownError(
                        throwable
                    )
                }
            }
        }
    }

    inline fun <reified T> handleErrors(code: String?, message: String?): ResponseState<T> {
        Timber.d("api => inside handleErrors code: $code, message: $message")
        return when (code) {
            "401" -> {
                Timber.d("api => handleCodeError AUTH_TOKEN_EXPIRED")
                ResponseState.ErrorState.Unauthorized(message ?: UNAUTHORIZED)
            }

            "402" -> {
                Timber.d("api => handleCodeError AUTH_TOKEN_EXPIRED")
                ResponseState.ErrorState.BadRequest(message ?: PAYMENT_REQUIRED)
            }

            "400", "403" -> {
                Timber.d("api => handleCodeError CL_BAD_REQUEST")
                ResponseState.ErrorState.BadRequest(message ?: BAD_REQUEST)
            }

            "404" -> {
                Timber.d("api => handleCodeError NO_DATA_FOUND")
                ResponseState.ErrorState.NotFound(message ?: NOT_DATA_FOUND)
            }

            "405" -> {
                Timber.d("api => handleCodeError CL_INVALID_CLIENT_TIME")
                ResponseState.ErrorState.MissingData(message ?: DATA_MISSING)
            }

            "500", "502", "505" -> {
                Timber.d("api => handleCodeError SERVER_ERROR")
                ResponseState.ErrorState.InternalServerError(message ?: INTERNAL_SERVER_ERROR)
            }
            /**
             * TODO -> Add more status code cases and define and return error states accordingly
             */
            null -> {
                Timber.d("api => handleCodeError code == null")
                ResponseState.ErrorState.UnknownError(message ?: NO_CODE_ERROR.format(message))
            }

            else -> {
                Timber.d("api => handleCodeError UnknownError ")
                ResponseState.ErrorState.UnknownError(
                    message ?: UNKNOWN_SERVER_ERROR.format(message)
                )
            }
        }
    }

    inline fun <reified T> SuperResponse.responseCheck(
        code: String?, message: String
    ): ResponseState<T> {
        return when (code) {
            "200", "201", "202" -> {
                Timber.d("api => responseCheck RESPONSE_SUCCESS")
                if (T::class == EmptyResponse::class) {
                    ResponseState.SuccessState(EmptyResponse as T)
                } else {
                    ResponseState.SuccessState(this as T)
                }
            }

            else -> {
                Timber.d("api => responseCheck else ")
                handleErrors(code, message)
            }
        }
    }


}
