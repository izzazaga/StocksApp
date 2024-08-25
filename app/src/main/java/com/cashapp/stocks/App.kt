package com.cashapp.stocks

import android.app.Application
import com.cashapp.stocks.stock.api.StockApi
import com.cashapp.stocks.stock.api.StockClient

class App: Application() {
    lateinit var stockApi: StockApi
        private set

    override fun onCreate() {
        super.onCreate()
        stockApi = StockClient.stockApi
    }
}