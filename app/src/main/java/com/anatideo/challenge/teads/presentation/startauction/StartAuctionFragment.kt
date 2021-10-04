package com.anatideo.challenge.teads.presentation.startauction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anatideo.challenge.teads.R
import com.anatideo.challenge.teads.databinding.StartAuctionFragmentBinding
import com.anatideo.challenge.teads.presentation.collectbids.CollectBidsFragment
import com.anatideo.challenge.teads.presentation.extensions.doAfterTextChanged
import com.anatideo.challenge.teads.presentation.extensions.observeOn
import com.anatideo.challenge.teads.presentation.extensions.shake
import com.anatideo.challenge.teads.presentation.main.MainViewModel
import com.anatideo.challenge.teads.presentation.model.AuctionViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartAuctionFragment : Fragment() {

    private lateinit var binding: StartAuctionFragmentBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StartAuctionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }

    private fun setViews() {
        setNewProduct()

        with(binding) {
            reservePriceInsert.doAfterTextChanged {
                value.text = it
                    .takeIf { it.isBlank().not() }
                    ?.let { "${getString(R.string.monetary_symbol)}$it" }
                    ?: getString(R.string.empty_info)
            }

            imageContainer.setOnClickListener {
                listOf(it, image, product).forEach { it.shake() }
                setNewProduct()
            }

            startAuction.setOnClickListener {
                mainViewModel.onStartingAuction(reservePriceInsert.text.toString())
            }
        }
    }

    private fun setNewProduct() {
        products.random().also {
            binding.image.setBackgroundResource(it.first)
            binding.product.text = getString(it.second)
        }
    }

    private fun setObservers() {
        mainViewModel.viewState.observeOn(this) {
            when (it) {
                AuctionViewState.MissingReservePrice -> binding.reservePriceInsert.shake()
                AuctionViewState.AuctionStarted -> {
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, CollectBidsFragment.newInstance())
                        .commitNow()
                }
            }
        }
    }

    companion object {
        fun newInstance() = StartAuctionFragment()

        private val products = listOf(
            R.drawable.product1010 to R.string.product_1,
            R.drawable.product1020 to R.string.product_2,
            R.drawable.product1030 to R.string.product_3,
            R.drawable.product1040 to R.string.product_4,
            R.drawable.product1050 to R.string.product_5,
            R.drawable.product1060 to R.string.product_6,
        )
    }
}