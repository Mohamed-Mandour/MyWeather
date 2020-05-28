package com.mando.myweather.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mando.myweather.R
import com.mando.myweather.model.Current
import com.mando.myweather.mvp.CurrentScreenContract
import com.mando.myweather.mvp.CurrentScreenPresenter
import kotlinx.android.synthetic.main.fragment_current.*

private const val TAG = "CurrentFragment"

class CurrentFragment : Fragment(), CurrentScreenContract.View{

    private lateinit var presenter: CurrentScreenContract.Presenter

    companion object {
        fun newInstance(fragmentManager: FragmentTransaction, current: Current?): CurrentFragment {
            val currentFragment = CurrentFragment()
            val bundle = Bundle()
            bundle.putParcelable("KEY", current)
            currentFragment.arguments = bundle
            fragmentManager.replace(R.id.mainActivityViewPager, currentFragment).commit()
            Log.d(TAG, "bundle: ${bundle.getParcelable<Current>("KEY")}")
            return currentFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        presenter = CurrentScreenPresenter(this)
        val current = arguments?.getParcelable<Current>("KEY")
        Log.d(TAG, "current: ${current?.temperature}")
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
        currentDay.text = day
    }

    override fun showSummary(summary: String?) {
        currentSummary.text = summary
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

    override fun showPrecipitationIcon(precipitationIcon: Drawable?) {
        precipitationImage.setImageDrawable(precipitationIcon)
    }

    override fun showPrecipitationText(precipitation: String?) {
        precipitationTextView.text = precipitation
    }

    override fun showPrecipitationValue(Precipitation: String?) {
        precipitationValue.text = Precipitation
    }

    override fun showPressureIcon(pressureIcon: Drawable?) {
        pressureImage.setImageDrawable(pressureIcon)
    }

    override fun showPressureText(pressure: String?) {
        pressureTextView.text = pressure
    }

    override fun showPressureValue(pressure: String?) {
        pressureValue.text = pressure
    }

    override fun showWindIcon(windIcon: Drawable?) {
        windImage.setImageDrawable(windIcon)
    }

    override fun showWindText(wind: String?) {
        windTextView.text = wind
    }

    override fun showWindValue(wind: String?) {
        windValue.text = wind
    }

    override fun showVisibilityIcon(visibilityIcon: Drawable?) {
        visibilityImage.setImageDrawable(visibilityIcon)
    }

    override fun showVisibilityText(visibilityText: String?) {
        visibilityTextView.text = visibilityText
    }

    override fun showVisibilityValue(visibility: String?) {
        visibilityValue.text = visibility
    }

    override fun showHumidityIcon(humidityIcon: Drawable?) {
        humidityImageView.setImageDrawable(humidityIcon)
    }

    override fun showHumidityText(humidityText: String?) {
        humidityTextView.text = humidityText
    }

    override fun showHumidityValue(humidity: String?) {
        humidityValue.text = humidity
    }

    override fun showCloudCoverIcon(cloudCoverIcon: Drawable?) {
        cloudCoverImage.setImageDrawable(cloudCoverIcon)
    }

    override fun showCloudCoverText(cloudCover: String?) {
        cloudTextView.text = cloudCover
    }

    override fun showCloudCoverValue(cloudCover: String?) {
        cloudCoverValue.text = cloudCover
    }
}
