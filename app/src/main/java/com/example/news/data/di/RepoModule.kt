package com.example.news.data.di

import com.example.news.data.repository.NewsDbRepoImpl
import com.example.news.data.repository.SearchRepoImpl
import com.example.news.data.repository.TopHeadlinesRepoImpl
import com.example.news.domain.repository.NewsDataBaseRepo
import com.example.news.domain.repository.SearchRepo
import com.example.news.domain.repository.TopHeadlinesRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Singleton
    @Binds
    abstract fun provideTopHeadlines(repoImpl: TopHeadlinesRepoImpl): TopHeadlinesRepo


    @Singleton
    @Binds
    abstract fun provideSearch(repoImpl: SearchRepoImpl): SearchRepo


    @Singleton
    @Binds
    abstract fun provideDBRepo(repo: NewsDbRepoImpl): NewsDataBaseRepo

}