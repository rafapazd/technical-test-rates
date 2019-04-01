package com.rpd.technicaltest.presentation.ui

import com.rpd.technicaltest.domain.rates.model.RateView
import io.reactivex.observers.DisposableSingleObserver

class RatesObserver(private val presenter: MainActivityPresenter): DisposableSingleObserver<List<RateView>>() {
    override fun onSuccess(rates: List<RateView>) {
        presenter.showRates(rates)
    }

    override fun onError(e: Throwable) {
        presenter.showError()
    }
}