package com.example.schema.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schema.data.models.Currency
import com.example.schema.data.models.CurrencyType
import com.example.schema.databinding.CurrencyCardBinding
import com.example.schema.databinding.DayCardBinding
import com.example.schema.util.Constants.VIEW_TYPE_CURRENCY_CARD
import com.example.schema.util.Constants.VIEW_TYPE_DAY_CARD


class CurrencyAdapter(private var currencyList: MutableList<Currency>, private val clicked : ClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    inner class CurrencyViewHolder(private val binding: CurrencyCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.apply {
                currencyName.text = currencyList[position].name
                currencyRateValue.text = currencyList[position].value
                currencyLayout.setOnClickListener {
                    clicked.selectedCurrencyClicked(currencyList[position])
                }
            }
        }
    }
    inner class DayViewHolder(private val binding: DayCardBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.apply {
                dateValue.text = currencyList[position].date
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currency = currencyList[position]
        return if(currency.type == CurrencyType.CURRENCY){
            VIEW_TYPE_CURRENCY_CARD
        }else{
            VIEW_TYPE_DAY_CARD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when(viewType){
            VIEW_TYPE_CURRENCY_CARD -> {
                val binding = CurrencyCardBinding.inflate(layoutInflater, parent, false)
                CurrencyViewHolder(binding)
            }
            else -> {
                val binding = DayCardBinding.inflate(layoutInflater, parent, false)
                DayViewHolder(binding)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       when(holder.itemViewType){
           VIEW_TYPE_CURRENCY_CARD -> {
               (holder as CurrencyViewHolder).bind(position)
           }
           VIEW_TYPE_DAY_CARD -> {
               (holder as DayViewHolder).bind(position)
           }
       }
    }

    override fun getItemCount(): Int {
       return currencyList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(newCurrencyList: MutableList<Currency>){
        currencyList = newCurrencyList
        notifyDataSetChanged()
    }
}