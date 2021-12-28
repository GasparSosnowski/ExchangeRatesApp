package com.example.schema.ui.details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.schema.data.models.Currency
import com.example.schema.repository.FakeRepository
import com.example.schema.util.Constants
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class DetailsViewModelTest{

    private lateinit var viewModel: DetailsViewModel

    @Test
    fun `getting init data successfully, sharedFlow returns Loading and Success event`() = runBlocking {
        val listOfCurrency = mutableListOf<Currency>()
        val firstCurrency = Currency(
            name = Constants.DAY,
            rate = "2000-12-1"
        )
        listOfCurrency.add(firstCurrency)
        (1..10).forEach {
            val secondCurrency = Currency(
                name = it.toString(),
                rate = it.toString()
            )
            listOfCurrency.add(secondCurrency)
        }

        val savedStateHandle = SavedStateHandle().apply {
            set("currencyList", listOfCurrency.toList())
            set("position", 10)
        }

        viewModel = DetailsViewModel(FakeRepository(), savedStateHandle)

        launch {
            viewModel.getInitData()
        }

        viewModel.detailsUIEvent.test {
            assertEquals(awaitItem(), DetailsViewModel.DetailsUIEvent.Loading)
            assertEquals(awaitItem(), DetailsViewModel.DetailsUIEvent.Success)
            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun `getting init data with problem, sharedFlow returns Loading and Error event`() = runBlocking {
        val listOfCurrency = mutableListOf<Currency>()
        val firstCurrency = Currency(
            name = "",
            rate = "2000-12-1"
        )
        listOfCurrency.add(firstCurrency)
        (1..10).forEach {
            val secondCurrency = Currency(
                name = it.toString(),
                rate = it.toString()
            )
            listOfCurrency.add(secondCurrency)
        }

        val savedStateHandle = SavedStateHandle().apply {
            set("currencyList", listOfCurrency.toList())
            set("position", 10)
        }

        viewModel = DetailsViewModel(FakeRepository(), savedStateHandle)

        launch {
            viewModel.getInitData()
        }

        viewModel.detailsUIEvent.test {
            assertEquals(awaitItem(), DetailsViewModel.DetailsUIEvent.Loading)
            assertEquals(awaitItem(), DetailsViewModel.DetailsUIEvent.Error("You have problem with currency list"))
            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun `getting init data successfully, stateFlow returns DetailsUIState with list of currency and day`() = runBlocking {
        val listOfCurrency = mutableListOf<Currency>()
        val firstCurrency = Currency(
            name = Constants.DAY,
            rate = "2000-12-1"
        )
        listOfCurrency.add(firstCurrency)
        (1..10).forEach {
            val secondCurrency = Currency(
                name = it.toString(),
                rate = it.toString()
            )
            listOfCurrency.add(secondCurrency)
        }

        val savedStateHandle = SavedStateHandle().apply {
            set("currencyList", listOfCurrency.toList())
            set("position", 10)
        }

        viewModel = DetailsViewModel(FakeRepository(), savedStateHandle)

        launch {
            viewModel.getInitData()
        }

        viewModel.detailsUIState.test {
            assertEquals(awaitItem(), DetailsViewModel.DetailsUIState(Currency("10","10"), "2000-12-1"))
            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun `getting init data with problem, stateFlow returns DetailsUIState with null `() = runBlocking {
        val listOfCurrency = mutableListOf<Currency>()
        val firstCurrency = Currency(
            name = "",
            rate = "2000-12-1"
        )
        listOfCurrency.add(firstCurrency)
        (1..10).forEach {
            val secondCurrency = Currency(
                name = it.toString(),
                rate = it.toString()
            )
            listOfCurrency.add(secondCurrency)
        }

        val savedStateHandle = SavedStateHandle().apply {
            set("currencyList", listOfCurrency.toList())
            set("position", 10)
        }

        viewModel = DetailsViewModel(FakeRepository(), savedStateHandle)

        launch {
            viewModel.getInitData()
        }

        viewModel.detailsUIState.test {
            assertEquals(awaitItem(), DetailsViewModel.DetailsUIState())
            cancelAndConsumeRemainingEvents()
        }
    }


}