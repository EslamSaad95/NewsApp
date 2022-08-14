package com.example.news.data.di

import android.content.Context
import androidx.room.Room
import com.example.news.data.repository.NewsDbRepoImpl
import com.example.news.data.room.NewsDao
import com.example.news.data.room.NewsDatabase
import com.example.news.domain.repository.NewsDataBaseRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseModule {
    @Provides
    fun provideChannelDao(appDatabase: NewsDatabase): NewsDao {
        return appDatabase.newsDao()
    }

    @Singleton
    @Binds
    abstract fun provideDBRepo(repo: NewsDbRepoImpl): NewsDataBaseRepo


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): NewsDatabase {
        return Room.databaseBuilder(
            appContext,
            NewsDatabase::class.java, "news"
        ).build()
    }
}