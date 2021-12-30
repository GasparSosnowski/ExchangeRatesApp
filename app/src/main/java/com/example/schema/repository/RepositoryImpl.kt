package com.example.schema.repository



import com.example.schema.data.models.Currency
import com.example.schema.data.modelsDto.RecentExchangeRatesDto
import com.example.schema.data.modelsDto.toCurrencyList
import com.example.schema.data.service.FixerApi
import com.example.schema.util.Constants
import com.example.schema.util.Resource

class RepositoryImpl(private val fixerApi: FixerApi) : Repository{

    override suspend fun getRecentExchangeRates(): Resource<MutableList<Currency>> {
        return try {
            val response : RecentExchangeRatesDto?  = fixerApi.getRecentExchangeRates(Constants.API_KEY)

            if(response != null){

                val result = response.toCurrencyList()

                Resource.Success(result)
            }else{
                Resource.Error("An error occurred")
            }

        }catch (e : Exception){
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun getNextDaysData(date : String): Resource<MutableList<Currency>>{
        return try {
            val response : RecentExchangeRatesDto?  = fixerApi.getNextDaysData(date, Constants.API_KEY)

            if(response != null){

                val result = response.toCurrencyList()

                Resource.Success(result)
            }else{
                Resource.Error("An error occurred")
            }

        }catch (e : Exception){
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}