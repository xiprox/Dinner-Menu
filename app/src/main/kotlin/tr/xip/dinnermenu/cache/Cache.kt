package tr.xip.dinnermenu.cache

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import tr.xip.dinnermenu.model.Menu

object Cache {
    private val FILE_NAME = "cache"

    private val KEY_MENU = "menu"

    private var cache: SharedPreferences? = null

    fun init(context: Context) {
        cache = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    fun saveMenu(menu: Menu) {
        crashIfCacheNull()
        cache!!.edit().putString(KEY_MENU, Gson().toJson(menu)).commit()
    }

    fun getMenu(): Menu? {
        crashIfCacheNull()
        val menu = cache!!.getString(KEY_MENU, null)
        return if (menu != null) Gson().fromJson(menu, Menu::class.java) else null
    }

    fun crashIfCacheNull() {
        if (Cache.cache == null) {
            throw NullPointerException("Cache SharedPreferences is null. Are you sure you are calling ${javaClass.simpleName}.init()?")
        }
    }
}