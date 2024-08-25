package com.cashapp.stocks.stock.api

import com.cashapp.stocks.stock.Stock
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    fun getStockData(): Flow<List<Stock>>
}