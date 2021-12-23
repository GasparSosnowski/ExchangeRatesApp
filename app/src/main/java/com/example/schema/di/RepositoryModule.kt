package com.example.schema.di

import com.example.schema.data.database.Database
import com.example.schema.data.service.FixerApi
import com.example.schema.repository.Repository
import com.example.schema.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        database: Database,
        fixerApi: FixerApi
    ) : Repository {
        return RepositoryImpl(database, fixerApi)
    }
}