package com.mando.weatherforecast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mando.weatherforecast.R
import com.mando.weatherforecast.utils.currentDateFormatter
import com.mando.weatherforecast.utils.formatTime
import com.mando.weatherforecast.utils.getTemperatureValue
import com.mando.weatherforecast.utils.lastUpdatedFormatter
import com.mando.weatherforecast.viewModel.CurrentViewModel
import com.mando.weatherforecast.viewModel.CurrentViewModelFactory
import kotlinx.android.synthetic.main.fragment_current.*

class CurrentFragment : Fragment() {

    private lateinit var viewModel: CurrentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        val context = activity?.applicationContext
        if (context != null) {
            val currentViewModelFactory = CurrentViewModelFactory(context)
            viewModel = ViewModelProviders.of(this, currentViewModelFactory)
                .get(CurrentViewModel::class.java)
            showCurrentWeather(viewModel)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current, container, false)
    }

    private fun showCurrentWeather(viewModel: CurrentViewModel) {
        var time = 0L
        viewModel.getCurrentForecast().observe(this, Observer { current ->
            currentSummary.text = current.summary
            precipitationValue.text = current.precipProbability.toString()
            temp.text = getTemperatureValue(current.temperature).toString()
            pressureValue.text = current.pressure.toString()
            windValue.text = current.windSpeed.toString()
            cloudCoverValue.text = current.cloudCover.toString()
            humidityValue.text = current.humidity.toString()
            visibilityValue.text = current.visibility.toString()
            time = current.time
        })

        viewModel.getTimeZone().observe(this, Observer { timeZone ->
            locationTimeZone.text = timeZone
            currentDay.text = formatTime(currentDateFormatter, timeZone, time)
            lastTimeUpdated.text = formatTime(lastUpdatedFormatter, timeZone, time)
        })
    }
}
