package com.example.currencyconverter.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://my.transfergo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: TransferGoApi = retrofit.create(TransferGoApi::class.java)

}