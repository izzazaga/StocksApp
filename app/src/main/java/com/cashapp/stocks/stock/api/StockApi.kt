package com.cashapp.stocks.stock.api

import com.cashapp.stocks.stock.Stock
import retrofit2.http.GET

interface StockApi {
    @GET("endpoint")
    suspend fun getStockData(): List<Stock>
}