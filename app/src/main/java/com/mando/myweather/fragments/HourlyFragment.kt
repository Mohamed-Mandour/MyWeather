package com.mando.myweather.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mando.myweather.R
import com.mando.myweather.background.ForecastJson
import com.mando.myweather.impl.ParseForecast
import com.mando.myweather.model.Hourly
import kotlinx.android.synthetic.main.daily_recycle_item.view.*
import kotlinx.android.synthetic.main.fragment_hourly.view.*
import timber.log.Timber
import java.lang.ref.WeakReference

class HourlyFragment : Fragment() {

    var hourlyList: Array<Hourly?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        FetchForecastTask(this).execute()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_hourly, container, false)
        with(root.recyclerView) {
            if (isAdded)
                recyclerView.layoutManager = LinearLayoutManager(activity)
                recyclerView.adapter = HourlyRecyclerViewAdapter(hourlyList)
        }
        return root
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(hourly: Hourly?) {
            Timber.d("HourlyRecyclerViewAdapter ViewHolder called()")
            view.time.text = hourly?.time.toString()
        }

    }

    class HourlyRecyclerViewAdapter(private val hourlyList: Array<Hourly?>?) :
        RecyclerView.Adapter<com.mando.adpater.HourlyRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): com.mando.adpater.HourlyRecyclerViewAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.daily_recycle_item, parent, false)
            return com.mando.adpater.HourlyRecyclerViewAdapter.ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return hourlyList?.size ?: 0
        }

        override fun onBindViewHolder(
            holder: com.mando.adpater.HourlyRecyclerViewAdapter.ViewHolder,
            position: Int
        ) {
            holder.bind(hourlyList?.get(position))
        }
    }
}

private class FetchForecastTask(val fragment: HourlyFragment) :
    AsyncTask<Void?, Void?, String>() {

    private val weakReference: WeakReference<HourlyFragment> =
        WeakReference(fragment)

    override fun doInBackground(vararg params: Void?): String? {
        return weakReference.let {
            fragment.activity.let { it1 ->
                it1?.let { it2 ->
                    ForecastJson(
                        it2
                    ).getForecastJson()
                }
            }
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        val parseForecast = ParseForecast(result)
        val forecast = parseForecast.parseForecastJson()
        val hourly: Array<Hourly?>? = forecast.getHourly()
        fragment.hourlyList = hourly
    }
}


