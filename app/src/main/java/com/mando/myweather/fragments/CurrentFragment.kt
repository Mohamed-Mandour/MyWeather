package com.mando.myweather.fragments

import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mando.myweather.R
import com.mando.myweather.background.ForecastJson
import com.mando.myweather.impl.ParseForecast
import com.mando.myweather.mvp.CurrentScreenContract
import com.mando.myweather.mvp.CurrentScreenPresenter
import kotlinx.android.synthetic.main.fragment_current.*
import java.lang.ref.WeakReference

private const val TAG = "CurrentFragment"

class CurrentFragment : Fragment(), CurrentScreenContract.View{

    private lateinit var presenter: CurrentScreenContract.Presenter

    companion object {
        fun newInstance(): CurrentFragment {
            return CurrentFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        presenter = CurrentScreenPresenter(this)
        FetchForecastTask(this).execute()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current, container, false)
    }

    override fun getContext() = activity

    override fun showWeatherIcon(icon: Drawable?) {
        iconWeather.setImageDrawable(icon)
    }

    override fun showLocationIcon(locationPin: Drawable?) {
        locationIcon.setImageDrawable(locationPin)
    }

    override fun showCurrentDay(day: String?) {
        CurrentDay.text = day
    }

    override fun showSummary(summary: String?) {
        CurrentSummary.text = summary
    }

    override fun showTimezone(timezone: String?) {
        locationTimeZone.text = timezone
    }

    override fun showDegreeIcon(degreeIcon: Drawable?) {
        degree.setImageDrawable(degreeIcon)
    }

    override fun showLastUpdated(lastUpdated: String?) {
        lastTimeUpdated.text = lastUpdated
    }

    override fun showWhiteLine(mainColor: Int) {
        whiteLine.setBackgroundColor(mainColor)
    }

    override fun showTemperature(currentTemperature: String?) {
        temp.text = currentTemperature
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
            val current = forecast.getCurrent()
            currentFragment.presenter.setCurrentForecast(current)
        }
    }
}
