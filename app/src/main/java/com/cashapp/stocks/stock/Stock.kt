package com.cashapp.stocks.stock

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    @SerializedName("ticker")
    val ticker: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("current_price_cents")
    val currentPriceCents: Int,

    @SerializedName("quantity")
    val quantity: Int? = null,

    @SerializedName("current_price_timestamp")
    val currentPriceTimestamp: Int
) {
    fun getFormattedPrice(): String {
        val sign = when (currency) {
            "USD" -> "$"
            else -> "€"
        }
        return "$sign${currentPriceCents / 100}.${currentPriceCents % 100}"
    }
}

@Serializable
data class StockResponse(
    @SerializedName("stocks")
    val stocks: List<Stock>
)