package com.anatideo.challenge.teads.presentation.ongoingauction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anatideo.challenge.teads.R
import com.anatideo.challenge.teads.databinding.OngoingAuctionFragmentBinding
import com.anatideo.challenge.teads.presentation.auctionresult.AuctionResultFragment
import com.anatideo.challenge.teads.presentation.collectbids.CollectBidsFragment
import com.anatideo.challenge.teads.presentation.base.extensions.observeOn
import com.anatideo.challenge.teads.presentation.main.MainViewModel
import com.anatideo.challenge.teads.presentation.model.AuctionViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OngoingAuctionFragment : Fragment() {

    private lateinit var binding: OngoingAuctionFragmentBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OngoingAuctionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }

    private fun setViews() {
        with(binding) {
            addNewBid.setOnClickListener {
                mainViewModel.onAddNewBid()
            }

            terminate.setOnClickListener {
                mainViewModel.onTerminate()
            }
        }
    }

    private fun setObservers() {
        mainViewModel.viewState.observeOn(this) {
            when (it) {
                AuctionViewState.AddNewBid -> {
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, CollectBidsFragment.newInstance())
                        .commitNow()
                }
                AuctionViewState.Terminate -> {
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, AuctionResultFragment.newInstance())
                        .commitNow()
                }
            }
        }
    }

    companion object {
        fun newInstance() = OngoingAuctionFragment()
    }
}