package com.mando.myweather.tabs

import com.mando.myweather.model.Current

interface MainScreenTab {

    interface View {
        fun showCurrentFragment()
        fun showHourlyFragment()
        fun showDailyFragment()
        fun showAlertFragment()
    }

}