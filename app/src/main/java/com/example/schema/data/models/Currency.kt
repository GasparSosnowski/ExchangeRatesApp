package com.example.schema.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
    val name : String = "",
    val value : String = "" ,
    val date : String = "",
    val type : CurrencyType
) : Parcelable
