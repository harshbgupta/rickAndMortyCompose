package corp.hell.kernel.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import corp.hell.kernel.constants.AppData.ctx
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.io.*
import java.nio.charset.StandardCharsets

fun getJsonFileFromAssetsDirAndReturnFileDataAsString(jsonFilePath: String): String {
    val jsonFile = ctx.resources.assets.open(jsonFilePath)
    val writer: Writer = StringWriter()
    val buffer = CharArray(1024)
    return try {
        val reader: Reader = BufferedReader(
            InputStreamReader(
                jsonFile, StandardCharsets.UTF_8
            )
        )
        var n: Int
        while (reader.read(buffer).also { n = it } != -1) {
            writer.write(buffer, 0, n)
        }
        jsonFile.close()
        Timber.d("callMockAPI getJson done ")
        writer.toString()
    } catch (e: java.lang.Exception) {
        Timber.d("callMockAPI UnsupportedEncodingException $e")
        "{\"message\": \"${e.message}\"}"
    }
}

/**
 * Copyright © 2023 56 AI Technologies. All rights reserved.
 *
 * @author: Harsh Gupta
 * @Date: March 15, 2023
 */

//Let it be as suspend function
suspend inline fun <reified T> mockApi(
    jsonFilePath: String?
): Response<T> {
    jsonFilePath ?: return Response.error(
        400,
        ResponseBody.create(null, "Mock Api Error: path is null")
    )
    return try {
        val jsonString: String = getJsonFileFromAssetsDirAndReturnFileDataAsString(jsonFilePath)
        val jsonObject = Gson().fromJson<T>(jsonString, object : TypeToken<T>() {}.type)
        val res: Response<T> = Response.success(jsonObject)
//        jsonObject //this is T type
        res
    } catch (e: Exception) {
        Timber.d("callMockAPI exception $e")
        Response.error<T>(400, ResponseBody.create(null, "Mock Api Error: ${e.message}"))
    }
}
