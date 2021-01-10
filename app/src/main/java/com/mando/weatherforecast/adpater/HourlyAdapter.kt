package com.mando.weatherforecast.adpater

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mando.weatherforecast.R
import com.mando.weatherforecast.model.CurrentResponse
import com.mando.weatherforecast.utils.*
import kotlinx.android.synthetic.main.daily_recycle_item.view.*
import timber.log.Timber

class HourlyAdapter : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    private var hourly = mutableListOf<CurrentResponse>()
    private var timeZone: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.daily_recycle_item)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Timber.d("getItemCount size ${hourly.size}")
        return hourly.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Timber.d("onBindViewHolder hourly ${hourly[position]}")
        holder.bind(hourly[position], timeZone)
    }

    fun setHourlyForecast(hourly: MutableList<CurrentResponse>, timeZone: String) {
        Timber.d("setHourlyForecast hourly $hourly")
        this.hourly.clear()
        this.hourly.addAll(hourly)
        this.timeZone = timeZone
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(hourlyList: CurrentResponse, timeZone: String) = with(view) {
            precipitationValue.text = hourlyList.precipProbability.toString()
            windValue.text = hourlyList.windSpeed.toString()
            temperatureValue.text = getTemperatureValue(hourlyList.temperature).toString()
            cloudCoverValue.text = hourlyList.cloudCover.toString()
            summary.text = hourlyList.summary.toString()
            time.text = formatTime(hourlyDateFormatter, timeZone, hourlyList.time)
            day.text = formatTime(dailyDateFormatter, timeZone, hourlyList.time)
        }
    }
}