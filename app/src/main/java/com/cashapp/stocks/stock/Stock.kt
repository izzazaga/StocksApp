package com.cashapp.stocks.stock

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    @SerialName("ticker")
    val ticker: String,

    @SerialName("name")
    val name: String,

    @SerialName("currency")
    val currency: String,

    @SerialName("current_price_cents")
    val currentPriceCents: Long,

    @SerialName("quantity")
    val quantity: Int? = null,

    @SerialName("current_price_timestamp")
    val currentPriceTimestamp: Long
) {
    fun getFormattedPrice(): String {
        println("$currentPriceCents")
        return "$${currentPriceCents / 100}.${currentPriceCents % 100}"
    }
}

@Serializable
data class StockResponse(
    @SerialName("stocks")
    val stocks: List<Stock>
)