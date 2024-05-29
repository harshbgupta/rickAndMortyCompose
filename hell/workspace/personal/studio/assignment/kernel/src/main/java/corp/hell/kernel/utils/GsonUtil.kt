package corp.hell.kernel.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> fromJsonString(json: String?): T? {
    return if(json!=null)Gson().fromJson(json, object : TypeToken<T>() {}.type) else null
}

fun <T> mapToObject(map: Map<String, Any?>?, type: Class<T>): T? {
    if (map == null) return null

    val json = Gson().toJson(map)
    return Gson().fromJson(json, type)
}


/**
 * Convert any object to Json String
 */
fun toJsonString(obj: Any?): String {
    return if (obj != null) Gson().toJson(obj) else ""
}
