package com.mando.weatherforecast.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mando.weatherforecast.R
import com.mando.weatherforecast.model.HourlyResponse
import kotlinx.android.synthetic.main.daily_recycle_item.view.*
import timber.log.Timber

class HourlyRecyclerViewAdapter(private val hourlyList: Array<HourlyResponse?>?) :
    RecyclerView.Adapter<HourlyRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_recycle_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hourlyList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(hourlyList?.get(position))
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(hourly: HourlyResponse?) {
            Timber.d("HourlyRecyclerViewAdapter ViewHolder called()")
            view.time.text = hourly?.time.toString()
        }

    }
}