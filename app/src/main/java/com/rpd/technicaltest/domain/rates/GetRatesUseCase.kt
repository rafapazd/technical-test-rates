package com.rpd.technicaltest.domain.rates

import com.rpd.technicaltest.data.rates.repository.RatesRepository
import com.rpd.technicaltest.domain.UseCase
import com.rpd.technicaltest.domain.rates.model.RateView
import io.reactivex.Scheduler
import io.reactivex.Single
import java.text.SimpleDateFormat
import javax.inject.Inject

class GetRatesUseCase @Inject constructor(
    private val ratesRepository: RatesRepository,
    subscribeScheduler: Scheduler,
    postExecutionScheduler: Scheduler
) : UseCase<List<RateView>, GetRatesUseCase.Params>(subscribeScheduler, postExecutionScheduler) {

    override fun buildUseCaseSingle(params: GetRatesUseCase.Params): Single<List<RateView>> = ratesRepository.getRates(params.startAt, params.endAt)
        .map {
            it.map {
                RateView(SimpleDateFormat("yyyy-MM-dd").parse(it.key), it.value.eur)
            }
        }

    class Params private constructor(val startAt: String, val endAt: String) {
        companion object {
            fun forRates(startAt: String, endAt: String): Params {
                return Params(startAt, endAt)
            }
        }
    }
}
