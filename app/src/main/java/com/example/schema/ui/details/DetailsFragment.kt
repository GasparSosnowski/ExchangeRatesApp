package com.example.schema.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.schema.R
import com.example.schema.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding : FragmentDetailsBinding
    private val viewModel : DetailsViewModel by viewModels()

    private var updateJob: Job? = null
    private var eventJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details,
            container,
            false
        )

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        eventJob = lifecycleScope.launch {
            viewModel.detailsUIEvent.collect { event ->
                when(event){
                    is DetailsViewModel.DetailsUIEvent.Success -> {
                        binding.apply {
                            progressBar.isVisible = false
                        }
                    }
                    is DetailsViewModel.DetailsUIEvent.Error -> {
                        Snackbar.make(
                            binding.root,
                            event.error,
                            Snackbar.LENGTH_SHORT
                        ).show()
                        binding.apply {
                            progressBar.isVisible = false
                        }
                    }
                    DetailsViewModel.DetailsUIEvent.Loading -> {
                        binding.apply {
                            progressBar.isVisible = true
                        }
                    }
                }
            }
        }

        updateJob = lifecycleScope.launch {
            viewModel.detailsUIState.collectLatest {
                binding.apply {
                    date.text = it.day
                    currencyName.text = it.currency?.name
                    currencyRate.text = it.currency?.rate
                }
            }
        }

    }

    override fun onStop() {
        eventJob?.cancel()
        updateJob?.cancel()
        super.onStop()
    }

}