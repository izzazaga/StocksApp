package com.cashapp.stocks.stock.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StockClient {
    private const val BASE_URL = "https://storage.googleapis.com/cash-homework/cash-stocks-api/"

    val stockApi: StockApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StockApi::class.java)
    }
}