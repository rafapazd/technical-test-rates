package com.rpd.technicaltest.data.rates.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class EURRateResponse(
    @SerializedName("EUR")
    @Expose
    var eur: Float)