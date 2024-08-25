package com.cashapp.stocks.stock.api

import com.cashapp.stocks.stock.Stock

sealed class StockState {
    data class Success(val stock: List<Stock>): StockState()
    data class Error(val message: String): StockState()
    data object Loading: StockState()
}