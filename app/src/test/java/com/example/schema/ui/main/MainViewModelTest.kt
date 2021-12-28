package com.example.schema.ui.main

import app.cash.turbine.test
import com.example.schema.data.models.Currency
import com.example.schema.repository.FakeRepository
import kotlinx.coroutines.*
import org.junit.Assert.*


import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Test
    fun `getting init data successfully, sharedFlow returns Loading and Success event`() = runBlocking {
        viewModel = MainViewModel(FakeRepository())

        launch {
            viewModel.getInitData()
        }

        viewModel.mainUIEvent.test {
            assertEquals(awaitItem(), MainViewModel.MainUIEvent.Loading)
            assertEquals(awaitItem(), MainViewModel.MainUIEvent.Success)
        }

    }

    @Test
    fun `getting next data successfully, sharedFlow returns Loading and Success event`() = runBlocking {
        viewModel = MainViewModel(FakeRepository())

        launch {
            viewModel.getNextData()
        }

        viewModel.mainUIEvent.test {
            assertEquals(awaitItem(), MainViewModel.MainUIEvent.Loading)
            assertEquals(awaitItem(), MainViewModel.MainUIEvent.Success)
        }

    }

    @Test
    fun `getting init data with error, sharedFlow returns Loading and Success event`() = runBlocking {
        val fakeRepository = FakeRepository()
        fakeRepository.setShouldReturnError(true)
        viewModel = MainViewModel(fakeRepository)

        launch {
            viewModel.getInitData()
        }

        viewModel.mainUIEvent.test {
            assertEquals(awaitItem(), MainViewModel.MainUIEvent.Loading)
            assertEquals(awaitItem(), MainViewModel.MainUIEvent.Error("Error"))
        }

    }

    @Test
    fun `getting next data with error, sharedFlow returns Loading and Success event`() = runBlocking {
        val fakeRepository = FakeRepository()
        fakeRepository.setShouldReturnError(true)
        viewModel = MainViewModel(fakeRepository)

        launch {
            viewModel.getNextData()
        }

        viewModel.mainUIEvent.test {
            assertEquals(awaitItem(), MainViewModel.MainUIEvent.Loading)
            assertEquals(awaitItem(), MainViewModel.MainUIEvent.Error("Error"))
        }

    }

    @Test
    fun `getting init data successfully, stateFlow returns mutable list of currency`() = runBlocking {
        viewModel = MainViewModel(FakeRepository())

        launch {
            viewModel.getInitData()
        }

        viewModel.mainUIState.test {
            assertEquals(awaitItem(), mutableListOf<Currency>())
        }
    }

    @Test
    fun `getting init data with problem, stateFlow returns null`() = runBlocking {
        val fakeRepository = FakeRepository()
        fakeRepository.setShouldReturnError(true)
        viewModel = MainViewModel(fakeRepository)

        launch {
            viewModel.getInitData()
        }

        viewModel.mainUIState.test {
            assertEquals(awaitItem(), null)
        }
    }

    @Test
    fun `getting next data successfully, stateFlow returns mutable list of currency`() = runBlocking {
        viewModel = MainViewModel(FakeRepository())

        launch {
            viewModel.getNextData()
        }

        viewModel.mainUIState.test {
            assertEquals(awaitItem(), mutableListOf<Currency>())
        }
    }

    @Test
    fun `getting next data with problem, stateFlow returns null`() = runBlocking {
        val fakeRepository = FakeRepository()
        fakeRepository.setShouldReturnError(true)
        viewModel = MainViewModel(fakeRepository)

        launch {
            viewModel.getNextData()
        }

        viewModel.mainUIState.test {
            assertEquals(awaitItem(), null)
        }
    }



}