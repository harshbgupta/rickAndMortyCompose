package corp.hell.kernel.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import corp.hell.kernel.constants.AppData.ctx
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "BabbleDataStore"

//Instance of DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

object DataStorePref {

    const val FIRST_LAUNCH_COMPLETED = "first_launch_completed"
    const val PHONE_NUMBER = "phone_number"
    const val USER_ID = "user_id"
    const val DIAL_CODE = "dial_code"
    const val AUTH_TOKEN = "auth_token"
    const val LANGUAGE = "language"
    const val PROFILE_URL = "profile_url"
    const val FULL_NAME = "full_name"
    const val PHONE_NUMBER_WITHOUT_DIAL_CODE = "phone_number_without_dial_code"

    ///////////////////////////////////////////////////////////////////////////
    // Remove From Data Store
    ///////////////////////////////////////////////////////////////////////////
    suspend inline fun <reified T> removeFromDataStore(key: String) {
        when (T::class) {
            String::class -> {
                ctx.dataStore.edit { pref ->
                    pref.remove(stringPreferencesKey(key))
                }
            }

            Long::class -> {
                ctx.dataStore.edit { pref ->
                    pref.remove(longPreferencesKey(key))
                }
            }

            Boolean::class -> {
                ctx.dataStore.edit { pref ->
                    pref.remove(booleanPreferencesKey(key))
                }
            }

            Int::class -> {
                ctx.dataStore.edit { pref ->
                    pref.remove(intPreferencesKey(key))
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Write to data
    ///////////////////////////////////////////////////////////////////////////
    fun writeToDataStore(map: Map<String, Any?>): Flow<Boolean> = flow {
        try {
            map.forEach { entry ->
                ctx.dataStore.edit { pref ->
                    when (entry.value) {
                        is Boolean -> {
                            pref[booleanPreferencesKey(entry.key)] = entry.value as Boolean
                        }

                        is Long -> {
                            pref[longPreferencesKey(entry.key)] = entry.value as Long
                        }

                        is String -> {
                            pref[stringPreferencesKey(entry.key)] = entry.value as String
                        }

                        is Int -> {
                            pref[intPreferencesKey(entry.key)] = entry.value as Int
                        }

                        is Double -> {
                            pref[doublePreferencesKey(entry.key)] = entry.value as Double
                        }
                    }
                }
            }
            emit(true)
        } catch (e: IOException) {
            emit(false)
        } catch (e: Exception) {
            emit(false)
        }
    }.flowOn(Dispatchers.IO)


    ///////////////////////////////////////////////////////////////////////////
    // Read from data store
    ///////////////////////////////////////////////////////////////////////////
    suspend inline fun <reified T> readFromDataStore(key: String): T? {
        when (T::class) {
            String::class -> {
                val data = ctx.dataStore.data.map { pref ->
                    pref[stringPreferencesKey(key)]
                }.firstOrNull()
                return if (data != null) {
                    data as T
                } else {
                    null
                }
            }

            Long::class -> {
                val data = ctx.dataStore.data.map { pref ->
                    pref[longPreferencesKey(key)]
                }.firstOrNull()
                return if (data != null) {
                    data as T
                } else {
                    null
                }
            }

            Boolean::class -> {
                val data = ctx.dataStore.data.map { pref ->
                    pref[booleanPreferencesKey(key)]
                }.firstOrNull()
                return if (data != null) {
                    data as T
                } else {
                    null
                }
            }

            Int::class -> {
                val data = ctx.dataStore.data.map { pref ->
                    pref[intPreferencesKey(key)]
                }.firstOrNull()
                return if (data != null) {
                    data as T
                } else {
                    null
                }
            }

            else -> {
                return null
            }
        }
    }
}


