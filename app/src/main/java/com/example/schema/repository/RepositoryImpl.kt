package com.example.schema.repository


import com.example.schema.data.database.Database
import com.example.schema.data.models.Currency
import com.example.schema.data.modelsDto.toCurrencyList
import com.example.schema.data.service.FixerApi
import com.example.schema.util.Constants

class RepositoryImpl(private val database: Database, private val fixerApi: FixerApi) : Repository{

    override suspend fun getRecentExchangeRates(): MutableList<Currency> {
        val recentExchangeRatesDto = fixerApi.getRecentExchangeRates(Constants.API_KEY)

        return recentExchangeRatesDto.toCurrencyList()
    }

    override suspend fun getNextDaysData(date : String): MutableList<Currency>{
        val recentExchangeRatesDto = fixerApi.getNextDaysData(date, Constants.API_KEY)

        return recentExchangeRatesDto.toCurrencyList()
    }
}