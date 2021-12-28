package com.example.schema.repository

import com.example.schema.data.models.Currency
import com.example.schema.util.Resource


interface Repository {

    suspend fun getRecentExchangeRates() : Resource<MutableList<Currency>>

    suspend fun getNextDaysData(date : String): Resource<MutableList<Currency>>
}