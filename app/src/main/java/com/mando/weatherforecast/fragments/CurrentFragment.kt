package com.mando.weatherforecast.fragments

import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airbnb.lottie.LottieAnimationView
import com.mando.weatherforecast.R
import com.mando.weatherforecast.network.NetworkStatusChecker
import com.mando.weatherforecast.utils.*
import com.mando.weatherforecast.viewModel.CurrentViewModel
import com.mando.weatherforecast.viewModel.CurrentViewModelFactory
import kotlinx.android.synthetic.main.fragment_current.*

private const val NO_CONNECTION_DIALOG = "NoConnectionDialog"

class CurrentFragment : Fragment() {

    private lateinit var viewModel: CurrentViewModel
    private val iconUtils: IconUtils = IconUtils()
    private val networkStatusChecker: NetworkStatusChecker?
        get() {
            return if (Build.VERSION.SDK_INT >= 23) {
                NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
            } else {
                return null
            }
        }

    companion object {
        fun newInstance(): CurrentFragment {
            return CurrentFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        val context = activity?.applicationContext
        if (context != null) {
            val currentViewModelFactory = CurrentViewModelFactory(context)

            viewModel = ViewModelProviders.of(this, currentViewModelFactory)
                .get(CurrentViewModel::class.java)

            networkStatusChecker?.checkConnectionToInternet {
                showCurrentWeather(viewModel)
            }

            if (networkStatusChecker?.hasInternetConnection() == false) {
                onConnectionDialog()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinnerView: LottieAnimationView = view.findViewById(R.id.spinner_animation_view)
        val temperature = view.findViewById<TextView>(R.id.temp)
        if (temperature.text.isEmpty() || temperature.text == null) {
            spinnerView.visibility = View.VISIBLE
            spinnerView.setOnClickListener { showCurrentWeather(viewModel)}
        } else {
            spinnerView.pauseAnimation()
        }
    }

    private fun onConnectionDialog() {
        activity?.supportFragmentManager?.let {
            NoConnectionDialog().show(
                it,
                NO_CONNECTION_DIALOG
            )
        }
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
            iconWeather.setImageResource(iconUtils.getIconId(current.icon))
        })

        viewModel.getTimeZone().observe(this, Observer { timeZone ->
            locationTimeZone.text = timeZone
            currentDay.text = formatTime(dailyDateFormatter, timeZone, time)
            lastTimeUpdated.text = formatTime(hourlyDateFormatter, timeZone, time)
        })
    }
}
