package com.example.news.data.room

import androidx.room.*

@Dao
interface NewsDao {

    @Query("SELECT * FROM newsdatabaseentity")
    suspend fun getAllTeams(): List<NewsDatabaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(vararg news: NewsDatabaseEntity)

    @Delete
    suspend fun delete(news: NewsDatabaseEntity)
}