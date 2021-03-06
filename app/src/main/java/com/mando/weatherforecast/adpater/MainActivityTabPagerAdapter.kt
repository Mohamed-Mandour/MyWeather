package com.mando.weatherforecast.adpater

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mando.weatherforecast.R
import com.mando.weatherforecast.fragments.CurrentFragment
import com.mando.weatherforecast.fragments.DailyFragment
import com.mando.weatherforecast.fragments.HourlyFragment

private const val FIRST_TAP = 0
private const val SECOND_TAP = 1

private val TAB_TITLE = arrayOf(
    R.string.current,
    R.string.hourly,
    R.string.daily
)

class MainActivityTabPagerAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            FIRST_TAP -> CurrentFragment.newInstance()
            SECOND_TAP -> HourlyFragment.newInstance()
            else -> DailyFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): String? {
        return context.getString(TAB_TITLE[position])
    }

    override fun getCount(): Int = TAB_TITLE.size
}