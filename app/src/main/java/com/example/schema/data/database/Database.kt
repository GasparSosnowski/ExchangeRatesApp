package com.example.schema.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.schema.data.models.Settings

@Database(entities = arrayOf(Settings::class), version = 1, exportSchema = false)
abstract class Database : RoomDatabase(){
    abstract fun dao() : Dao
}