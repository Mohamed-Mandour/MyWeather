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

class CurrentFragment : Fragment() {

    companion object {
        fun newInstance() = CurrentFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val currentLayout = inflater.inflate(R.layout.fragment_current, container, false)
        val current = Current()
        Log.d(TAG, "current ${current.icon}")
        return currentLayout
    }

}
