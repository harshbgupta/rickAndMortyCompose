package corp.hell.kernel.network

import com.google.gson.JsonElement

/**
 * Super Response
 */
open class SuperResponse

/**
 * Base response type used for all requests
 */
data class BaseResponse<T>(
    var code: String?,
    var message: String?,
    val data: T?,
    val paging: Paging? = null,
    val meta: JsonElement? = null
) : SuperResponse()

data class Paging(
    val id: String?,
    val page: String?,
)

/**
 *  For Request with no response data
 */
data class Item(
    val id: String
) : SuperResponse()

/**
 *  for Empty response
 */
object EmptyResponse : SuperResponse()

/**
 *  for Error response
 */
data class ErrorResp(val message: String, val code: String) : SuperResponse()