package tr.xip.dinnermenu.ui.fragment

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_day.*
import tr.xip.dinnermenu.R
import tr.xip.dinnermenu.ext.logw
import tr.xip.dinnermenu.ext.logwtf
import tr.xip.dinnermenu.ext.toPx
import tr.xip.dinnermenu.model.Day
import tr.xip.dinnermenu.ui.activity.MainActivity
import tr.xip.dinnermenu.ui.widget.ObservableScrollView

class DayFragment : Fragment() {
    private var day: Day? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        day = arguments.getParcelable<Day>(ARG_DAY)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_day, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        if (day == null) {
            logw { "Day cannot be null; returning." }
            return
        }

        val item = day!!

        /* Main dish */
        if (item.main != null && item.main!!.isNotEmpty()) {
            mainDish.text = item.main
        } else {
            mainDishHolder.visibility = View.GONE
        }

        /* Side dish */
        if (item.side != null && item.side!!.isNotEmpty()) {
            sideDish.text = item.side
        } else {
            sideDishHolder.visibility = View.GONE
        }

        /* Soup */
        if (item.soup != null && item.soup!!.isNotEmpty()) {
            soup.text = item.soup
        } else {
            soupHolder.visibility = View.GONE
        }

        /* Dessert */
        if (item.dessert != null && item.dessert!!.isNotEmpty()) {
            dessert.text = item.dessert
        } else {
            dessertHolder.visibility = View.GONE
        }

        /* Salad */
        if (item.salad != null && item.salad!!.isNotEmpty()) {
            salad.text = item.salad
        } else {
            saladHolder.visibility = View.GONE
        }

        /* Appetizer */
        if (item.appetizer != null && item.appetizer!!.isNotEmpty()) {
            appetizer.text = item.appetizer
        } else {
            appetizerHolder.visibility = View.GONE
        }

        /* ObservableScrollView callbacks for Toolbar shadow */
        scrollView.setCallbacks(object : ObservableScrollView.CallbacksAdapter() {
            var shadowVisible = false

            override fun onScrollChanged(scrollY: Int) {
                if (scrollY > 0) showShadow() else showShadow(false)
            }

            fun showShadow(show: Boolean = true) {
                if (shadowVisible == show) return

                var animator = ValueAnimator.ofFloat(if (show) 4f else 0f)
                animator.duration = 200
                animator.addUpdateListener {
                    ((activity) as MainActivity).setAppBarElevation((animator.animatedValue as Float).toPx(context).toFloat())
                }
                animator.start()

                shadowVisible = show
            }
        })
    }

    companion object {
        val ARG_DAY = "day"

        fun newInstance(day: Day?): DayFragment {
            var dayFragment = DayFragment()
            var bundle = Bundle()
            bundle.putParcelable(ARG_DAY, day)
            dayFragment.arguments = bundle
            return dayFragment
        }
    }
}