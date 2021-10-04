package com.anatideo.challenge.teads.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anatideo.challenge.teads.databinding.StartAuctionFragmentBinding
import com.anatideo.challenge.teads.presentation.extensions.observeOn
import com.anatideo.challenge.teads.presentation.extensions.shake
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
        with(binding) {
            startAuction.setOnClickListener {
                mainViewModel.onStartingAuction(reservePrice.text.toString())
            }
        }
    }

    private fun setObservers() {
        mainViewModel.viewState.observeOn(this) {
            when (it) {
                AuctionViewState.MissingReservePrice -> binding.reservePrice.shake()
                AuctionViewState.AuctionStarted -> println("start collecting bids")
            }
        }
    }

    companion object {
        fun newInstance() = StartAuctionFragment()
    }
}