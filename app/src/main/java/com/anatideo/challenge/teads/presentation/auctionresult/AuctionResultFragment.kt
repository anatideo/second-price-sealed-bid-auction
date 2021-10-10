package com.anatideo.challenge.teads.presentation.auctionresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anatideo.challenge.teads.R
import com.anatideo.challenge.teads.databinding.AuctionResultFragmentBinding
import com.anatideo.challenge.teads.presentation.base.ImageProvider
import com.anatideo.challenge.teads.presentation.base.extensions.observeOn
import com.anatideo.challenge.teads.presentation.main.MainViewModel
import com.anatideo.challenge.teads.presentation.model.AuctionViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuctionResultFragment : Fragment() {

    private lateinit var binding: AuctionResultFragmentBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuctionResultFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()

        mainViewModel.onAuctionResult()
    }

    private fun setObservers() {
        mainViewModel.viewState.observeOn(this) {
            when (it) {
                is AuctionViewState.ShowWinner -> {
                    val winner = ImageProvider.bidderImages.random()
                    binding.image.setImageResource(winner)

                    binding.result.text = String.format(
                        getString(R.string.sold),
                        it.auctionResult.bidder.name,
                        it.auctionResult.winningPrice.toString()
                    )
                }
                AuctionViewState.ShowInsufficientHighestBid -> {
                    val product = ImageProvider.products.random()
                    binding.image.setImageResource(product.first)
                    binding.result.text = getString(R.string.insufficient_bid_values)
                }
                AuctionViewState.ShowNoSingularWinner -> {
                    binding.image.setImageResource(R.drawable.warning)
                    binding.result.text = getString(R.string.no_singular_winner)
                }
                AuctionViewState.ShowUnknownError -> {
                    binding.image.setImageResource(R.drawable.warning)
                    binding.result.text = getString(R.string.unknown_error)
                }
            }
        }
    }

    companion object {
        fun newInstance() = AuctionResultFragment()
    }
}