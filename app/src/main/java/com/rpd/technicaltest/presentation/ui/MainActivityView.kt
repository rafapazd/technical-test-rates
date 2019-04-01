package com.rpd.technicaltest.presentation.ui

import com.rpd.technicaltest.domain.rates.model.RateView

interface MainActivityView {
    fun initialiseView()
    fun showRates(rateViews: List<RateView>)
    fun resetChart()
}