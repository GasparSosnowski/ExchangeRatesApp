package com.example.schema.data.database

import androidx.room.Dao
import androidx.room.Insert
import com.example.schema.data.models.Settings

@Dao
interface Dao {

    @Insert
    suspend fun insertSettings(settings: Settings)

}