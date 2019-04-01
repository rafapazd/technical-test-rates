package com.rpd.technicaltest.data

import com.rpd.technicaltest.data.rates.model.RatesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface Service {
    @GET("history?base=USD&symbols=EUR")
    fun getRates(
        @Query("start_at") startAt: String,
        @Query("end_at") endAt: String
    ): Single<RatesResponse>
}