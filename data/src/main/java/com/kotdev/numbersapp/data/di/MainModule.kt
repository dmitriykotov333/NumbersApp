package com.kotdev.numbersapp.data.di

import android.content.Context
import com.kotdev.numbersapp.core.network.NetworkObserver
import com.kotdev.numbersapp.data.HistoryRepository
import com.kotdev.numbersapp.data.datastore.FilterPreferences
import com.kotdev.numbersapp.database.FactDatabase
import com.kotdev.numbersapp.navigation.core.AppNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideNavigator() = AppNavigator()

    @Singleton
    @Provides
    fun provideNetwork(@ApplicationContext context: Context) = NetworkObserver(context)

    @Provides
    fun providePagingRepository(
        dao: FactDatabase
    ): HistoryRepository {
        return HistoryRepository(dao.historyDao)
    }

    @Provides
    fun provideFilterPreferences(
        @ApplicationContext context: Context
    ): FilterPreferences {
        return FilterPreferences(context)
    }

}