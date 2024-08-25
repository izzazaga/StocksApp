package com.cashapp.stocks.stock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cashapp.stocks.R
import com.cashapp.stocks.stock.Stock

class StockAdapter(private val data: List<Stock>, private val clickListener: (Stock) -> Unit) :
    RecyclerView.Adapter<StockAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tickerView: TextView = view.findViewById(R.id.stock_ticker)
        val textView: TextView = view.findViewById(R.id.stock_name)
        val priceView: TextView = view.findViewById(R.id.stock_price)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.stock_recycler_view_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tickerView.text = data[position].ticker
        viewHolder.textView.text = data[position].name
        viewHolder.priceView.text = data[position].getFormattedPrice()
        viewHolder.itemView.setOnClickListener {
            clickListener(data[position])
        }
    }

    override fun getItemCount() = data.size

}
