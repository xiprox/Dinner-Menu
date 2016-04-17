package tr.xip.dinnermenu.ui.activity

import android.animation.*
import android.content.Intent
import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import tr.xip.dinnermenu.R
import tr.xip.dinnermenu.cache.Cache
import tr.xip.dinnermenu.ext.*
import tr.xip.dinnermenu.model.Menu
import tr.xip.dinnermenu.network.api.Client
import tr.xip.dinnermenu.ui.view.OnPageChangeListenerAdapter
import tr.xip.dinnermenu.ui.adapter.DayFragmentPagerAdapter
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), Callback<Menu> {
    private var adapter: DayFragmentPagerAdapter? = null

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /* Page control Buttons and date TextView */
        nextDay.setOnClickListener {
            if (pager.currentItem != pager.adapter.count - 1) pager.currentItem++
        }
        nextDay.setOnLongClickListener {
            pager.currentItem = pager.adapter.count - 1
            true
        }
        previousDay.setOnClickListener {
            if (pager.currentItem != 0) pager.currentItem--
        }
        previousDay.setOnLongClickListener {
            pager.currentItem = 0
            true
        }
        date.setOnClickListener {
            goToToday()
        }
        retry.setOnClickListener {
            fetchMenu()
        }
        pager.addOnPageChangeListener(object : OnPageChangeListenerAdapter() {
            override fun onPageSelected(position: Int) = notifyPageSelected(position)
        })

        /* Fetch data */
        fetchMenu()

        /*
                var i = 0
                while (i < 10) {
                    Handler().postDelayed({
                        showButtons(i % 2 == 0)
                    }, 2000)
                    i++
                }
        */
    }

    private fun fetchMenu() {
        setAppBarElevation(0f)
        showButtons(false)

        flipper.safeSetDisplayedChild(FLIPPER_PROGRESS)

        val cal = Calendar.getInstance().toSimpleDate()
        val cachedMenu = Cache.getMenu()

        if (cachedMenu != null && cachedMenu.month == cal.getMonth() && cachedMenu.year == cal.getYear()) {
            success(cachedMenu, true)
        } else {
            Client.getMenu(cal.getMonth(), cal.getYear()).enqueue(this)
        }
    }

    fun setAppBarElevation(elevation: Float) {
        appBar.elevation = elevation
    }

    override fun onCreateOptionsMenu(optionsMenu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.main, optionsMenu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResponse(call: Call<Menu>, response: Response<Menu>) {
        var menu = response.body()
        if (response.isSuccessful && menu != null) success(menu)
        else fail(response = response)
        notifyDataChanged()
    }

    override fun onFailure(call: Call<Menu>?, t: Throwable?) {
        fail(throwable = t)
        notifyDataChanged()
    }

    private fun success(menu: Menu, fromCache: Boolean = false) {
        if (menu.days?.size ?: 0 == 0) {
            showNoData()
            return
        }

        this.menu = menu

        adapter = DayFragmentPagerAdapter(menu.days, supportFragmentManager)
        pager.adapter = adapter

        flipper.safeSetDisplayedChild(FLIPPER_CONTENT)

        goToToday()

        /* Finally, cache the freshly downloaded data. */
        if (!fromCache) {
            Cache.saveMenu(menu)
        }

        showButtons()
    }

    private fun fail(response: Response<Menu>? = null, throwable: Throwable? = null) {
        var image = R.drawable.es_unknown_error
        var title = getString(R.string.error_unknown_title)
        var subtitle = getString(R.string.error_unknown_subtitle)

        if (throwable != null) {
            if (throwable.cause.toString().contains("GaiException")) {
                image = R.drawable.es_no_connection
                title = getString(R.string.error_connection_title)
                subtitle = getString(R.string.error_connection_subtitle)
            }

            loge { "CAUSE: " + throwable.cause }
        }

        if (response != null) {
            title = "${response.code()}"
            subtitle = response.message() + "\n\n" + response.errorBody().string()
        }

        errorImage.setImageResource(image)
        errorTitle.text = title
        errorSubtitle.text = subtitle

        flipper.safeSetDisplayedChild(FLIPPER_ERROR)

        showButtons(false)
    }

    private fun showNoData() {
        errorImage.setImageResource(R.drawable.es_no_data)
        errorTitle.text = getString(R.string.error_no_data_title)
        errorSubtitle.text = getString(R.string.error_no_data_subtitle)
        flipper.safeSetDisplayedChild(FLIPPER_ERROR)
        showButtons(false)
    }

    private fun goToToday() {
        if (adapter!!.days != null) {
            val today = Calendar.getInstance().toSimpleDate().getDay()
            for ((index, value) in adapter!!.days!!.withIndex()) {
                if (value.day == today) {
                    pager.currentItem = index
                    break
                }
            }
        }
    }

    private fun showButtons(show: Boolean = true) {
        appBarButtons.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun notifyPageSelected(position: Int) {
        if (menu == null || menu?.days == null || adapter == null) return

        previousDay.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
        nextDay.visibility = if (position == menu!!.days!!.size - 1) View.INVISIBLE else View.VISIBLE

        val year = menu!!.year
        val month = menu!!.month
        val day = adapter!!.days!![position].day

        var cal = Calendar.getInstance().toSimpleDate()
        cal.set(year!!, month!! - 1, day!!)

        when (cal) {
            Calendar.getInstance().toSimpleDate().shiftUp(1) -> {
                date.text = getString(R.string.tomorrow)
            }
            Calendar.getInstance().toSimpleDate() -> {
                date.text = getString(R.string.today)
            }
            Calendar.getInstance().toSimpleDate().shiftDown(1) -> {
                date.text = getString(R.string.yesterday)
            }
            else -> {
                val format = SimpleDateFormat("d MMMM")
                date.text = format.format(cal.toTimestamp())
            }
        }
    }

    private fun notifyDataChanged() {
        if (menu == null || menu?.days == null || menu?.days?.size == 0) {
            setAppBarElevation(2.toPx(this).toFloat())
        }
    }

    companion object {
        val FLIPPER_PROGRESS = 0
        val FLIPPER_CONTENT = 1
        val FLIPPER_ERROR = 2
    }
}
