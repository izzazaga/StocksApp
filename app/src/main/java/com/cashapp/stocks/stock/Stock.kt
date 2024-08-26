package com.cashapp.stocks.stock

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Serializable
data class Stock(
    @SerializedName("ticker")
    val ticker: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("currency")
    val currency: String = "USD",

    @SerializedName("current_price_cents")
    val currentPriceCents: Long = 0,

    @SerializedName("quantity")
    val quantity: Int? = null,

    @SerializedName("current_price_timestamp")
    val currentPriceTimestamp: Long = 0
) {

    private fun formatPrice(price: Long): String {
        val res = price % 100
        return when {
            price == 0L -> "0.00"
            res < 10 -> "${price / 100}.0${res}"
            else -> "${price / 100}.${price % 100}"
        }
    }

    private fun getSign() = when (currency) {
        "USD" -> "$"
        else -> "€"
    }

    fun getFormattedPrice(): String {
        return "${getSign()}${formatPrice(currentPriceCents)}"
    }

    fun getDateFormatted(): String {
        val date = Date(currentPriceTimestamp)
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        return format.format(date)
    }

    fun getTotalStockValue(): String {
        return if (quantity == null) {
            ""
        } else {
            "${getSign()}${formatPrice(quantity * currentPriceCents)}"
        }
    }
}

@Serializable
data class StockResponse(
    @SerializedName("stocks")
    val stocks: List<Stock>
)