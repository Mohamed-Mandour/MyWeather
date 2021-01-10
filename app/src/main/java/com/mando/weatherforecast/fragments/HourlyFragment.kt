package com.mando.weatherforecast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mando.weatherforecast.R
import com.mando.weatherforecast.adpater.HourlyAdapter
import com.mando.weatherforecast.utils.inflate
import com.mando.weatherforecast.viewModel.CurrentViewModel
import com.mando.weatherforecast.viewModel.CurrentViewModelFactory
import com.mando.weatherforecast.viewModel.HourlyViewModel
import com.mando.weatherforecast.viewModel.HourlyViewModelFactory
import kotlinx.android.synthetic.main.fragment_hourly.*

class HourlyFragment : Fragment() {

    private lateinit var hourlyViewModel: HourlyViewModel
    private lateinit var currentViewModel: CurrentViewModel

    private var adapter = HourlyAdapter()


    companion object {
        fun newInstance(): HourlyFragment {
            return HourlyFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        val context = activity?.applicationContext
        if (context != null) {
            val hourlyViewModelFactory = HourlyViewModelFactory(context)
            val currentViewModelFactory = CurrentViewModelFactory(context)
            hourlyViewModel =
                ViewModelProviders.of(this, hourlyViewModelFactory).get(HourlyViewModel::class.java)
            currentViewModel = ViewModelProviders.of(this, currentViewModelFactory)
                .get(CurrentViewModel::class.java)
            getHourlyForecast()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_hourly)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            if (isAdded){
                recyclerView.layoutManager = LinearLayoutManager(activity)
                recyclerView.adapter = adapter
        }
    }

    private fun getHourlyForecast() {
        var timeZone = ""
        currentViewModel.getTimeZone().observe(this, Observer { timeZoneObserver ->
            timeZone = timeZoneObserver
        })
        hourlyViewModel.getHourlyForecast().observe(this, Observer { hourly ->
            adapter.setHourlyForecast(hourly.data, timeZone)
        })
    }
}


