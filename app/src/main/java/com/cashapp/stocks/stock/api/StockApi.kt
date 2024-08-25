package com.cashapp.stocks.stock.api

import com.cashapp.stocks.stock.Stock
import com.cashapp.stocks.stock.StockResponse
import retrofit2.http.GET

interface StockApi {
    @GET("portfolio.json")
    suspend fun getStockData(): StockResponse
    @GET("portfolio_malformed.json")
    suspend fun getMalformedStockData(): StockResponse
    @GET("portfolio_empty.json")
    suspend fun getEmptyStockData(): StockResponse
}