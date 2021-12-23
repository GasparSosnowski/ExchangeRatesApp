package com.example.schema.ui.details


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schema.data.models.Currency
import com.example.schema.repository.Repository
import com.example.schema.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: Repository, private val savedStateHandle: SavedStateHandle): ViewModel() {

    private val _detailsUIEvent = MutableSharedFlow<DetailsUIEvent>()
    val detailsUIEvent = _detailsUIEvent.asSharedFlow()

    private val _detailsUIState = MutableStateFlow(DetailsUIState())
    val detailsUIState = _detailsUIState.asStateFlow()

    private val currencyList : List<Currency>? = savedStateHandle.get<List<Currency>>("currencyList")
    private val position : Int? = savedStateHandle.get<Int>("position")

    init {
        getInitData()
    }

    private fun getInitData() = viewModelScope.launch {
        try {
            _detailsUIEvent.emit(DetailsUIEvent.Loading)

            val day = findDay(currencyList, position)

            if(day != null){
                val detailsUIState = DetailsUIState(currencyList!![position!!], day)
                _detailsUIState.value = detailsUIState
                _detailsUIEvent.emit(DetailsUIEvent.Success)
            }else{
                _detailsUIEvent.emit(DetailsUIEvent.Error("You have problem with currency list"))
            }
        }
        catch (e : Exception){
            _detailsUIEvent.emit(DetailsUIEvent.Error(e.message.toString()))
        }
    }

    private fun findDay(currencyList : List<Currency>?, position : Int?) : String?{
        if(currencyList != null && position != null){
            for (i in position downTo 0 step 1){
                if(currencyList[i].name == Constants.DAY){
                    return currencyList[i].rate
                }
            }
        }
        return null
    }

    sealed class DetailsUIEvent{
        object Loading : DetailsUIEvent()
        object Success: DetailsUIEvent()
        data class Error(val error : String) : DetailsUIEvent()
    }

    data class DetailsUIState(
        val currency: Currency? = null,
        val day: String? = null
    )
}