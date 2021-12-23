package com.example.schema.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
data class Settings(

    @PrimaryKey(autoGenerate = true)
    val id : Int,

    val userName : String,

)

