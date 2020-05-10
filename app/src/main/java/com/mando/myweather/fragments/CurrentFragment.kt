package com.mando.myweather.fragments

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
        FetchForecastTask().execute()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current, container, false)
    }

    private fun setupCurrent() {
        if (isAdded) {
            summary.text = current?.summary
            locationAddress.text = current?.timezone
        }
    }

    private inner class FetchForecastTask: AsyncTask<Void?, Void?, String>() {

        override fun doInBackground(vararg params: Void?): String? {
            return activity?.let { ForecastJson(it).getForecastJson() }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val parseForecast = ParseForecast(result)
            val forecast = parseForecast.parseForecastJson()
            current = forecast.getCurrent()
            setupCurrent()
        }
    }

}
