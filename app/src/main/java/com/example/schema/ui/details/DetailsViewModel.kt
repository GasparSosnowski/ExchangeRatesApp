package com.example.schema.ui.details


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.schema.data.models.Currency
import com.example.schema.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(savedStateHandle: SavedStateHandle): ViewModel() {

    private val _detailsUIEvent = MutableSharedFlow<DetailsUIEvent>()
    val detailsUIEvent = _detailsUIEvent.asSharedFlow()

    private val _detailsUIState = MutableStateFlow<Currency?>(null)
    val detailsUIState = _detailsUIState.asStateFlow()

    private val currency : Currency? = savedStateHandle.get<Currency>(Constants.CURRENCY)

    init {
        getInitData()
    }

    fun getInitData() = viewModelScope.launch(Dispatchers.IO) {
        try {
            _detailsUIEvent.emit(DetailsUIEvent.Loading)

            if(currency != null){
                _detailsUIState.value = currency
                _detailsUIEvent.emit(DetailsUIEvent.Success)
            }else{
                _detailsUIEvent.emit(DetailsUIEvent.Error("You have problem with currency list"))
            }
        }
        catch (e : Exception){
            _detailsUIEvent.emit(DetailsUIEvent.Error(e.message.toString()))
        }
    }


    sealed class DetailsUIEvent{
        object Loading : DetailsUIEvent()
        object Success: DetailsUIEvent()
        data class Error(val error : String) : DetailsUIEvent()
    }

}