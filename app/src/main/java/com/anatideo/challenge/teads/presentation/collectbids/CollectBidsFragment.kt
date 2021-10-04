package com.anatideo.challenge.teads.presentation.collectbids

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anatideo.challenge.teads.R
import com.anatideo.challenge.teads.databinding.CollectBidsFragmentBinding
import com.anatideo.challenge.teads.presentation.base.ErrorFeedback
import com.anatideo.challenge.teads.presentation.base.ImageProvider
import com.anatideo.challenge.teads.presentation.base.extensions.doAfterTextChanged
import com.anatideo.challenge.teads.presentation.base.extensions.observeOn
import com.anatideo.challenge.teads.presentation.base.extensions.shake
import com.anatideo.challenge.teads.presentation.main.MainViewModel
import com.anatideo.challenge.teads.presentation.model.AuctionViewState
import com.anatideo.challenge.teads.presentation.ongoingauction.OngoingAuctionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectBidsFragment : Fragment() {

    private lateinit var binding: CollectBidsFragmentBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CollectBidsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }

    private fun setViews() {
        with(binding) {
            setBidderImage()

            idInsert.doAfterTextChanged {
                id.text = it
                    .takeIf { it.isBlank().not() }
                    ?: getString(R.string.empty_info)
            }

            nameInsert.doAfterTextChanged {
                name.text = it.takeIf { it.isBlank().not() } ?: getString(R.string.no_name)
            }

            valueInsert.doAfterTextChanged {
                value.text = it
                    .takeIf { it.isBlank().not() }
                    ?.let { "${getString(R.string.monetary_symbol)}$it" }
                    ?: getString(R.string.empty_info)
            }

            addBid.setOnClickListener {
                mainViewModel.onAddingBid(
                    id.text.toString(),
                    name.text.toString(),
                    valueInsert.text.toString()
                )
            }

            imageContainer.setOnClickListener {
                listOf(it, image).forEach { it.shake() }
                setBidderImage()
            }
        }
    }

    private fun setBidderImage() {
        binding.image.setBackgroundResource(ImageProvider.bidderImages.random())
    }

    private fun setObservers() {
        mainViewModel.viewState.observeOn(this) {
            when (it) {
                AuctionViewState.MissingId -> binding.idInsert.shake()
                AuctionViewState.MissingBidValue -> binding.valueInsert.shake()
                AuctionViewState.BidAdded -> {
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, OngoingAuctionFragment.newInstance())
                        .commitNow()
                }
                AuctionViewState.ShowUnknownError -> {
                    ErrorFeedback.show(
                        getString(R.string.unknown_error),
                        this@CollectBidsFragment.context
                    )
                }
            }
        }
    }

    companion object {
        fun newInstance() = CollectBidsFragment()
    }
}