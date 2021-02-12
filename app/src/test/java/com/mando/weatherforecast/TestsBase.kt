package com.mando.weatherforecast

import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

abstract class TestBase {

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

}