package com.cashapp.stocks.stock.api

import com.cashapp.stocks.stock.Stock
import com.cashapp.stocks.stock.StockResponse
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    fun getStockData(): Flow<StockResponse>
    fun getMalformedStockData(): Flow<StockResponse>
    fun getEmptyStockData(): Flow<StockResponse>
}