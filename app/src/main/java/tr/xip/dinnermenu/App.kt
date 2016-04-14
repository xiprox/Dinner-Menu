package tr.xip.dinnermenu

import android.app.Application
import tr.xip.dinnermenu.cache.Cache
import tr.xip.dinnermenu.network.api.Client

class App : Application() {

    override fun onCreate() {
        Client.init()
        Cache.init(this)
        super.onCreate()
    }
}