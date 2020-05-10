package com.mando.myweather.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mando.myweather.R
import com.mando.myweather.background.ForecastJson
import com.mando.myweather.impl.ParseForecast
import com.mando.myweather.model.Current
import kotlinx.android.synthetic.main.fragment_current.*
import java.lang.ref.WeakReference

private const val TAG = "CurrentFragment"
private const val CURRENT = "current_forecast"

class CurrentFragment : Fragment() {

    private var current: Current? = null

    companion object {
        fun newInstance(): CurrentFragment {
            return CurrentFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        FetchForecastTask(this).execute()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current, container, false)
    }

    private fun setupCurrent() {
        if (isAdded) {
            time.text = current?.getTime
            temp.text = current?.getTemperture().toString()
            summary.text = current?.summary
            locationAddress.text = current?.timezone
        }
    }

    private class FetchForecastTask(val currentFragment: CurrentFragment) :
        AsyncTask<Void?, Void?, String>() {

        private val activityReference: WeakReference<CurrentFragment> =
            WeakReference(currentFragment)

        override fun doInBackground(vararg params: Void?): String? {
            return activityReference.let { currentFragment.activity?.let { it1 -> ForecastJson(it1).getForecastJson() } }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val parseForecast = ParseForecast(result)
            val forecast = parseForecast.parseForecastJson()
            currentFragment.current = forecast.getCurrent()
            currentFragment.setupCurrent()
        }
    }

}
