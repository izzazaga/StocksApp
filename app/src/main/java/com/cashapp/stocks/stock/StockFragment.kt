package com.cashapp.stocks.stock

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cashapp.stocks.databinding.StockFragmentBinding
import com.cashapp.stocks.stock.adapter.StockAdapter
import com.cashapp.stocks.stock.api.StockRepositoryImpl
import com.cashapp.stocks.stock.api.StockState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StockFragment: Fragment() {
    private lateinit var binding: StockFragmentBinding
    private val viewModel: StockViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return activity?.application?.let { StockRepositoryImpl(application = it) }
                    ?.let { StockViewModel(it) } as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StockFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        viewModel.fetchStocks()
    }

    private fun setListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is StockState.Success -> {
                            binding.stockProgressBar.visibility = View.GONE
                            binding.stockRecyclerView.adapter = StockAdapter(it.stock) { stock ->
                                viewModel.updateSelectedStock(stock)
                                showToast(stock)
                            }
                        }
                        is StockState.Error -> {
                            binding.stockProgressBar.visibility = View.GONE
                            showErrorDialog(it)
                        }
                        StockState.Loading -> {
                            binding.stockProgressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun showToast(stock: Stock) {
        Toast.makeText(
            context,
            "Clicked: ${stock.name}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showErrorDialog(error: StockState.Error) {
        CoroutineScope(Dispatchers.Main).launch {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                .setMessage(error.message)
                .setTitle("Error")
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}
