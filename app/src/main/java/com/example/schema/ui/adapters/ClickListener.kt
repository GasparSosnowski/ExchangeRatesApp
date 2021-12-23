package com.example.schema.ui.adapters

import com.example.schema.data.models.Currency

interface ClickListener {

    fun selectedCurrencyClicked(currencyList : List<Currency>, position : Int)

}