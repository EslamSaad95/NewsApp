package com.example.news.data.di

import com.example.footballLeague.data.repository.TopHeadlinesRepoImpl
import com.example.footballLeague.domain.repository.TopHeadlinesRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule
{
    @Singleton
    @Binds
    abstract fun provideTopHeadlines(repoImpl: TopHeadlinesRepoImpl): TopHeadlinesRepo
}