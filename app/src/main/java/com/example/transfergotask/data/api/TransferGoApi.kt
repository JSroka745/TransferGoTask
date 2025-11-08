package com.example.transfergotask.data.api

import com.example.transfergotask.data.model.FxRateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TransferGoApi {
    @GET("api/fx-rates")
    suspend fun getFxRates(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Float
    ): FxRateResponse
}