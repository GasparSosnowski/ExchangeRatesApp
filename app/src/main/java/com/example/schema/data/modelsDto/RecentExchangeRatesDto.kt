package com.example.schema.data.modelsDto

import RatesDto
import com.example.schema.data.models.Currency
import com.example.schema.util.Constants
import kotlin.reflect.full.memberProperties

data class RecentExchangeRatesDto(
    val base: String,
    val date: String,
    val rates: RatesDto,
    val success: Boolean,
    val timestamp: Int
)

fun RecentExchangeRatesDto.toCurrencyList(): MutableList<Currency> {
    val listOfCurrency = mutableListOf<Currency>()
    val currencyZero = Currency(
        name = Constants.DAY,
        rate = date
    )
    listOfCurrency.add(currencyZero)
    for (prop in RatesDto::class.memberProperties) {
        val currency = Currency(
            name = "${prop.name}:",
            rate = prop.get(rates).toString()
        )
        listOfCurrency.add(currency)
    }
    return listOfCurrency
}