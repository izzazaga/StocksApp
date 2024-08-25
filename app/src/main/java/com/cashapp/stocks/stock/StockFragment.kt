package com.cashapp.stocks.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cashapp.stocks.databinding.StockFragmentBinding


class StockFragment: Fragment() {
    private val viewModel: StockViewModel by viewModels()
    private lateinit var _binding: StockFragmentBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StockFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }
}