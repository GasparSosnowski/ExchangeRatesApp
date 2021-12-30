package com.example.schema.data.modelsDto

import com.example.schema.data.models.Currency
import com.example.schema.data.models.CurrencyType
import com.example.schema.util.formatAfterDot
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
    listOfCurrency.add(Currency(date = date, type = CurrencyType.DATE))
    for (prop in RatesDto::class.memberProperties) {
        val currency = Currency(
            name = "${prop.name}:",
            value = formatAfterDot(prop.get(rates).toString(), 4),
            date = date,
            type = CurrencyType.CURRENCY
        )
        listOfCurrency.add(currency)
    }
    return listOfCurrency
}