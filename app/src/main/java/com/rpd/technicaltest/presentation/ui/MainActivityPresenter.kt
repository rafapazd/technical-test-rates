package com.rpd.technicaltest.presentation.ui

import com.rpd.technicaltest.domain.rates.GetRatesUseCase
import com.rpd.technicaltest.domain.rates.model.RateView
import com.rpd.technicaltest.mvp.BasePresenter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(private val getRatesUseCase: GetRatesUseCase) : BasePresenter<MainActivityView>() {

    override fun initialise() {
        getView()?.initialiseView()
    }

    override fun disposeSubscriptions() {
        getRatesUseCase.dispose()
    }

    fun validateDates(startCal:Calendar, endCal:Calendar){
        if (areValidDates(startCal, endCal)){
            fetchRates(SimpleDateFormat("yyyy-MM-dd").format(startCal.time), SimpleDateFormat("yyyy-MM-dd").format(endCal.time))
        } else {
            showError()
        }
    }

    fun fetchRates(startDate:String, endDate:String){
        getRatesUseCase.execute(RatesObserver(this), GetRatesUseCase.Params.forRates(startDate, endDate))
    }

    fun showRates(rates: List<RateView>) {
        if(!rates.isEmpty()) {
            getView()?.showRates(rates.sortedWith(compareBy { it.date }))
        } else {
            showError()
        }
    }

    fun showError() {
        getView()?.resetChart();
    }

    fun areValidDates(startCal:Calendar, endCal:Calendar): Boolean {
        return (startCal.compareTo(endCal) < 0)
    }


}