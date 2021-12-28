package com.example.schema.repository

import com.example.schema.data.models.Currency
import com.example.schema.util.Resource
import java.lang.Error

class FakeRepository : Repository {

    private var shouldReturnError = false

    private val currencyList = mutableListOf<Currency>()

    fun setShouldReturnError(value : Boolean){
        shouldReturnError = value
    }

    override suspend fun getRecentExchangeRates(): Resource<MutableList<Currency>> {
        if(shouldReturnError){
            return Resource.Error("Error")
        }else{
            return Resource.Success(currencyList)
        }
    }

    override suspend fun getNextDaysData(date: String): Resource<MutableList<Currency>> {
        if(shouldReturnError){
            return Resource.Error("Error")
        }else{
            return Resource.Success(currencyList)
        }
    }
}