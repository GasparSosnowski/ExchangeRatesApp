package com.example.schema.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.schema.R
import com.example.schema.data.models.Currency
import com.example.schema.databinding.CurrencyCardBinding
import com.example.schema.util.Constants


class CurrencyAdapter(private var currencyList: MutableList<Currency>, private val clicked : ClickListener) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>(){


    inner class CurrencyViewHolder(val binding: CurrencyCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CurrencyCardBinding.inflate(layoutInflater, parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.binding.apply {
            firstTextView.text = currencyList[position].name
            secondTextView.text = currencyList[position].rate
            currencyLayout.setOnClickListener {
                if(currencyList[position].name != Constants.DAY){
                    clicked.selectedCurrencyClicked(currencyList, position)
                }
            }
            val context = holder.itemView.context
            if(currencyList[position].name == Constants.DAY){
                currencyLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.myBlack2))
                firstTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
                secondTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
                secondTextView.typeface = Typeface.DEFAULT_BOLD
            }else{
                currencyLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.myBlack))
                firstTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
                secondTextView.setTextColor(ContextCompat.getColor(context, R.color.myGreen))
                secondTextView.typeface = Typeface.DEFAULT
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