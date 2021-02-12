package com.mando.weatherforecast.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import com.mando.weatherforecast.TestBase
import com.mando.weatherforecast.model.CurrentResponse
import com.mando.weatherforecast.repository.ForecastRepository
import com.nhaarman.mockito_kotlin.given
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

//@RunWith(MockitoJUnitRunner::class)
//class CurrentViewModelTest: TestBase() {
//
//    private lateinit var currentViewModel: CurrentViewModel
//
//    @Mock
//    private lateinit var forecastRepository: ForecastRepository
//
//    @Mock
//    private lateinit var liveData: LiveData<CurrentResponse>
//
//    @Mock
//    private lateinit var context: Context
//
//    @Before
//    fun setUp() {
//        currentViewModel  = CurrentViewModel(context)
//
//    }
//
//}