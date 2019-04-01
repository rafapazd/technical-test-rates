package com.rpd.technicaltest.data.rates.repository

import com.rpd.technicaltest.data.Service
import com.rpd.technicaltest.data.rates.model.EURRateResponse
import io.reactivex.Single
import javax.inject.Inject


class RatesRepository @Inject constructor(private val service: Service) {
    fun getRates(startAt: String, endAt: String): Single<Map<String, EURRateResponse>> =
        service.getRates(startAt, endAt)
            .map { it.rates }
}