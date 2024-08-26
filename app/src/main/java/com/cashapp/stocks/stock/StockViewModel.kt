package com.cashapp.stocks.stock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cashapp.stocks.stock.api.StockRepository
import com.cashapp.stocks.stock.api.StockState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StockViewModel(private val repository: StockRepository) : ViewModel() {

    private val _state = MutableStateFlow<StockState>(StockState.Loading)
    val state: StateFlow<StockState> get() = _state.asStateFlow()
    var selectedStock: Stock? = null

    fun fetchStocks() {
        viewModelScope.launch {
            repository.getStockData()
                .map { response ->
                    val stocks = response?.stocks
                    if (!stocks.isNullOrEmpty()) {
                        StockState.Success(stocks)
                    } else {
                        StockState.Error("No stocks available")
                    }
                }
                .catch { e ->
                    _state.value = StockState.Error("Error fetching stocks: ${e.message}")
                }
                .collect { stockState ->
                    _state.value = stockState
                }
        }
    }

    fun updateSelectedStock(stock: Stock) {
        selectedStock = stock
    }
}