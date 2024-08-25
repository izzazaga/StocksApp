package com.cashapp.stocks.stock.api

import android.app.Application
import com.cashapp.stocks.App
import com.cashapp.stocks.stock.Stock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StockRepositoryImpl(application: Application) : StockRepository {

    private val stockApi = (application as App).stockApi

    override fun getStockData(): Flow<List<Stock>> = flow {
        try {
            val networkStocks = stockApi.getStockData()
            emit(networkStocks)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}