package com.example.schema.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schema.R
import com.example.schema.data.models.Currency
import com.example.schema.databinding.FragmentMainBinding
import com.example.schema.ui.adapters.ClickListener
import com.example.schema.ui.adapters.CurrencyAdapter
import com.example.schema.util.navigateSafely
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), ClickListener {

    private lateinit var binding : FragmentMainBinding
    private val viewModel : MainViewModel by viewModels()
    private lateinit var adapter: CurrencyAdapter

    private var updateJob: Job? = null
    private var eventJob: Job? = null

    var isScrolling = false
    var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        adapter = CurrencyAdapter(mutableListOf<Currency>(), this)
        binding.currencyList.adapter = adapter


        val scrollListener = object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCont = layoutManager.itemCount

                val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCont
                val isNotAtBeginning = firstVisibleItemPosition >= 0


                if(isAtLastItem && isNotAtBeginning && isScrolling && !isLoading){
                    viewModel.getNextData()
                    isScrolling = false
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true
                }
            }
        }

        binding.apply {
            currencyList.addOnScrollListener(scrollListener)
        }

        return binding.root
    }

    override fun selectedCurrencyClicked(currencyList: List<Currency>, position: Int) {
        val bundle = bundleOf("currencyList" to currencyList, "position" to position)
        findNavController().navigateSafely(R.id.action_mainFragment_to_detailsFragment, bundle)
    }


    override fun onStart() {
        super.onStart()

        eventJob = lifecycleScope.launch {
            viewModel.mainUIEvent.collect { event ->
                when(event){
                    is MainViewModel.MainUIEvent.Success -> {
                        binding.apply {
                            progressBar.isVisible = false
                        }
                        isLoading = false
                    }
                    is MainViewModel.MainUIEvent.Error -> {
                        Snackbar.make(
                            binding.root,
                            event.error,
                            Snackbar.LENGTH_SHORT
                        ).show()
                        binding.apply {
                            progressBar.isVisible = false
                        }
                        isLoading = false
                    }
                    is MainViewModel.MainUIEvent.Loading -> {
                        binding.apply {
                            progressBar.isVisible = true
                        }
                        isLoading = true
                    }
                }
            }
        }

        updateJob = lifecycleScope.launch {
            viewModel.mainUIState.collectLatest { value: MutableList<Currency>? ->
                if (value != null) {
                    adapter.setData(value)
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