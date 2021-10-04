package com.anatideo.challenge.teads.presentation.startauction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anatideo.challenge.teads.R
import com.anatideo.challenge.teads.databinding.StartAuctionFragmentBinding
import com.anatideo.challenge.teads.presentation.base.ErrorFeedback
import com.anatideo.challenge.teads.presentation.base.ImageProvider
import com.anatideo.challenge.teads.presentation.collectbids.CollectBidsFragment
import com.anatideo.challenge.teads.presentation.base.extensions.doAfterTextChanged
import com.anatideo.challenge.teads.presentation.base.extensions.observeOn
import com.anatideo.challenge.teads.presentation.base.extensions.shake
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
        ImageProvider.products.random().also {
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
                AuctionViewState.ShowUnknownError -> {
                    ErrorFeedback.show(
                        getString(R.string.unknown_error),
                        this@StartAuctionFragment.context
                    )
                }
            }
        }
    }

    companion object {
        fun newInstance() = StartAuctionFragment()
    }
}