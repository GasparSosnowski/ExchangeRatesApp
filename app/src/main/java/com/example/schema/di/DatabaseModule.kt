package com.example.schema.di

import android.content.Context
import androidx.room.Room
import com.example.schema.data.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) : Database{
        return Room.databaseBuilder(
            appContext,
            Database::class.java,
            "database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}