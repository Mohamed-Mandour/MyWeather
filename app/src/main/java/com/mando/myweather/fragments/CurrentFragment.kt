package com.mando.myweather.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mando.myweather.R
import com.mando.myweather.model.Current

private const val TAG = "CurrentFragment"
private const val CURRENT = "current_forecast"
class CurrentFragment : Fragment() {

    private var current: Current? = null
    companion object {
        fun newInstance(): CurrentFragment {
//            val bundle = Bundle()
//            bundle.putParcelable(CURRENT, current)
//            val currentFragment: CurrentFragment = CurrentFragment()
//            currentFragment.arguments = bundle
            return CurrentFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        current = arguments?.getParcelable(CURRENT)
        val currentLayout = inflater.inflate(R.layout.fragment_current, container, false)
        Log.d(TAG, "current $current")
        return currentLayout
    }

}
