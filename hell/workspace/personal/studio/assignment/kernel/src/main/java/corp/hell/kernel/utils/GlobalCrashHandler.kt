package corp.hell.kernel.utils

import android.os.Handler
import android.os.Looper
import androidx.annotation.Keep


@Keep
class GlobalCrashHandler {
    private var mCrashHandler: CrashHandler? = null
    private var instance: GlobalCrashHandler? = null

    fun init(crashHandler: CrashHandler) {
        getInstance()?.setCrashHandler(crashHandler)
    }

    private fun getInstance(): GlobalCrashHandler? {
        if (instance == null) {
            synchronized(GlobalCrashHandler::class.java) {
                if (instance == null) {
                    instance = GlobalCrashHandler()
                }
            }
        }
        return instance
    }

    private fun setCrashHandler(crashHandler: CrashHandler) {
        mCrashHandler = crashHandler
        Handler(Looper.getMainLooper()).post {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    if (mCrashHandler != null) {
                        mCrashHandler!!.uncaughtException(Looper.getMainLooper().thread, e)
                    }
                }
            }
        }
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            if (mCrashHandler != null) {
                mCrashHandler!!.uncaughtException(t, e)
            }
        }
    }


}

@Keep
interface CrashHandler {
    fun uncaughtException(t: Thread, e: Throwable)
}

