package corp.hell.archer

import android.app.Application
import android.content.pm.PackageManager
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import corp.hell.kernel.constants.AppData.BUILD_FLAVOR_DEV
import corp.hell.kernel.constants.AppData.ctx
import corp.hell.kernel.constants.AppData.mPackageName
import corp.hell.kernel.constants.AppData.mVersionCode
import corp.hell.kernel.constants.AppData.mVersionName
import corp.hell.kernel.utils.TimberDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        ctx = applicationContext
        if (BuildConfig.DEBUG) {
            //For Release it's not recommended to do logging, so not doing it
            //For Debug -> Inlining Timber for Debug
            Timber.plant(TimberDebugTree())
//            Timber.plant(Timber.DebugTree())
        }
        if (BuildConfig.FLAVOR == BUILD_FLAVOR_DEV) {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
        } else {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        }
        mPackageName = applicationContext.packageName
//        createNotificationChannel()
        try {
            val pInfo = this.packageManager.getPackageInfo(packageName, 0)
            val versionName = pInfo.versionName
            val versionCode = pInfo.versionCode
            mVersionName = versionName
            mVersionCode = versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
//        crashHandler()
    }

    private fun crashHandler() {
        // initialise no kill
//        GlobalCrashHandler().init(object : CrashHandler {
//            override fun uncaughtException(t: Thread, e: Throwable) {
//                e.printStackTrace()
//                showCrashPopUp.postValue(System.currentTimeMillis())
//                FirebaseCrashlytics.getInstance().recordException(e)
//            }
//        })
    }
}