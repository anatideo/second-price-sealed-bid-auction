package com.anatideo.challenge.teads.presentation.collectbids

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anatideo.challenge.teads.R
import com.anatideo.challenge.teads.databinding.CollectBidsFragmentBinding
import com.anatideo.challenge.teads.presentation.extensions.observeOn
import com.anatideo.challenge.teads.presentation.extensions.shake
import com.anatideo.challenge.teads.presentation.main.MainViewModel
import com.anatideo.challenge.teads.presentation.model.AuctionViewState
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
            imgPlaceholder.setBackgroundResource(bidderImages.random())

            idInsert.fillBidderAfterTextChanged(id)
            nameInsert.fillBidderAfterTextChanged(textView = name, placeholder = getString(R.string.no_name))
            valueInsert.fillBidderAfterTextChanged(textView = value, prefix = getString(R.string.monetary_symbol))

            addBid.setOnClickListener {
                mainViewModel.onAddingBid(
                    id.text.toString(),
                    name.text.toString(),
                    valueInsert.text.toString()
                )
            }
        }
    }

    private fun setObservers() {
        mainViewModel.viewState.observeOn(this) {
            when (it) {
                AuctionViewState.MissingId -> binding.idInsert.shake()
                AuctionViewState.MissingBidValue -> binding.valueInsert.shake()
                AuctionViewState.BidAdded -> println("ready to next step")
            }
        }
    }

    private fun EditText.fillBidderAfterTextChanged(
        textView: TextView,
        prefix: String? = null,
        placeholder: String? = null
    ) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                val text = editable.toString()
                val value = if (text.isBlank()) {
                    placeholder ?: getString(R.string.bidder_data_placeholder)
                } else {
                    prefix?.let { "$it$text" } ?: text
                }

                textView.text = value
            }
        })
    }

    companion object {
        fun newInstance() = CollectBidsFragment()

        private val bidderImages = listOf(
            R.drawable.punk1626,
            R.drawable.punk4643,
            R.drawable.punk5175,
            R.drawable.punk7569,
            R.drawable.punk9194,
            R.drawable.punk9463,
            R.drawable.punk9740,
            R.drawable.punk9859
        )
    }
}