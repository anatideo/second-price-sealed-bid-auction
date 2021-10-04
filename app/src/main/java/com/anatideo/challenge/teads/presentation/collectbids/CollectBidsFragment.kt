package com.anatideo.challenge.teads.presentation.collectbids

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anatideo.challenge.teads.databinding.CollectBidsFragmentBinding
import com.anatideo.challenge.teads.presentation.extensions.observeOn
import com.anatideo.challenge.teads.presentation.main.MainViewModel
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

        }
    }

    private fun setObservers() {
        mainViewModel.viewState.observeOn(this) {
            when (it) {

            }
        }
    }

    companion object {
        fun newInstance() = CollectBidsFragment()
    }
}