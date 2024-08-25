package com.cashapp.stocks.stock.api

import com.cashapp.stocks.stock.Stock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StockRepositoryImpl(
    private val stockApi: StockApi
) : StockRepository {

    override fun getStockData(): Flow<List<Stock>> = flow {
        try {
            val networkStocks = stockApi.getStockData() // Network call to fetch stocks
            emit(networkStocks)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}