package tr.xip.dinnermenu.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import tr.xip.dinnermenu.model.Day
import tr.xip.dinnermenu.ui.fragment.DayFragment

class DayFragmentPagerAdapter(val days: MutableList<Day>?, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return DayFragment.newInstance(if (days != null) days[position] else null)
    }

    override fun getItemPosition(`object`: Any?): Int = days?.indexOf(`object`) ?: -1

    override fun getCount(): Int = days?.size ?: 0
}
