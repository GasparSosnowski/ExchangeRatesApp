package com.example.schema.ui.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schema.data.models.Currency
import com.example.schema.repository.Repository
import com.example.schema.util.toString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository : Repository) : ViewModel() {

    private val _mainUIEvent = MutableSharedFlow<MainUIEvent>()
    val mainUIEvent = _mainUIEvent.asSharedFlow()

    private val _mainUIState = MutableStateFlow<MutableList<Currency>?>(null)
    val mainUIState = _mainUIState.asStateFlow()

    private val calendar = Calendar.getInstance()


    init {
        getInitData()
    }

    private fun getInitData() = viewModelScope.launch(Dispatchers.IO) {
        try {
            _mainUIEvent.emit(MainUIEvent.Loading)
            val data = repository.getRecentExchangeRates()

            _mainUIState.value = data
            _mainUIEvent.emit(MainUIEvent.Success)
        }
        catch (e : Exception){
            _mainUIEvent.emit(MainUIEvent.Error(e.message.toString()))
        }
    }

    fun getNextData() = viewModelScope.launch(Dispatchers.IO) {
        try {
            _mainUIEvent.emit(MainUIEvent.Loading)

            calendar.add(Calendar.DATE, -1)
            val today = calendar.time.toString("yyyy-MM-dd")

            val data = repository.getNextDaysData(today)
            val finalData : MutableList<Currency> = (_mainUIState.value!! + data) as MutableList<Currency>

            _mainUIState.value = finalData
            _mainUIEvent.emit(MainUIEvent.Success)

        }
        catch (e : Exception){
            _mainUIEvent.emit(MainUIEvent.Error(e.message.toString()))
        }
    }


    sealed class MainUIEvent{
        object Loading : MainUIEvent()
        object Success : MainUIEvent()
        data class Error(val error : String) : MainUIEvent()
    }

}