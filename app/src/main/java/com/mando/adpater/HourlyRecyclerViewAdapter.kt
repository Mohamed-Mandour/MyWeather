package com.mando.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mando.myweather.R
import com.mando.myweather.model.Hourly
import kotlinx.android.synthetic.main.daily_recycle_item.view.*
import timber.log.Timber

class HourlyRecyclerViewAdapter(private val hourlyList: Array<Hourly?>?) :
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
        fun bind(hourly: Hourly?) {
            Timber.d("HourlyRecyclerViewAdapter ViewHolder called()")
            view.time.text = hourly?.time.toString()
        }

    }
}