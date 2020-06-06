package com.mando.myweather.fragments


import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mando.myweather.R
import com.mando.myweather.background.ForecastJson
import com.mando.myweather.impl.ParseForecast
import com.mando.myweather.model.Hourly
import com.mando.myweather.mvp.CurrentScreenContract
import com.mando.myweather.mvp.CurrentScreenPresenter
import java.lang.ref.WeakReference

class DailyFragment : Fragment() {

//    private lateinit var presenter: DailyScreenContract.Presenter

    companion object {
        fun newInstance() = DailyFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
//        presenter = CurrentScreenPresenter(this)
//        DailyFragment.FetchForecastTask(this).execute()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily, container, false)
    }

    private class FetchForecastTask(val fragment: DailyFragment) :
        AsyncTask<Void?, Void?, String>() {

        private val weakReference: WeakReference<DailyFragment> =
            WeakReference(fragment)

        override fun doInBackground(vararg params: Void?): String? {
            return weakReference.let { fragment.activity.let { it1 -> it1?.let { it2 ->
                ForecastJson(
                    it2
                ).getForecastJson()
            } } }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val parseForecast = ParseForecast(result)
            val forecast = parseForecast.parseForecastJson()
            val hourly: Array<Hourly?>? = forecast.getHourly()
//            fragment.presenter.setCurrentForecast(hourly)
        }
    }
}
