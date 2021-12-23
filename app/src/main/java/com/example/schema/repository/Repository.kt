package com.example.schema.repository

import com.example.schema.data.models.Currency



interface Repository {

    suspend fun getRecentExchangeRates() : MutableList<Currency>

    suspend fun getNextDaysData(date : String): MutableList<Currency>
}