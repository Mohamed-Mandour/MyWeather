package com.mando.myweather.tabs

interface MainScreenTab {

    interface View {
        fun showCurrentFragment()
        fun showHourlyFragment()
        fun showDailyFragment()
        fun showAlertFragment()
    }

}