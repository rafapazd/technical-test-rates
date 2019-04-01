package com.rpd.technicaltest.data.rates.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RatesResponse(
    @SerializedName("base")
    @Expose
    var base: String? = null,
    @SerializedName("rates")
    @Expose
    var rates: Map<String,EURRateResponse>? = null,
    @SerializedName("start_at")
    @Expose
    var startAt: String? = null,
    @SerializedName("end_at")
    @Expose
    var endAt: String? = null
)
