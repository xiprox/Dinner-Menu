package tr.xip.dinnermenu

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import tr.xip.dinnermenu.cache.Cache
import tr.xip.dinnermenu.network.api.Client

class App : Application() {

    override fun onCreate() {
        Client.init()
        Cache.init(this)
        Fabric.with(this, Crashlytics())
        super.onCreate()
    }
}