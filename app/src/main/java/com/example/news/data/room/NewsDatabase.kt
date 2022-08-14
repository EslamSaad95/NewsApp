package com.example.news.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsDatabaseEntity::class], version = 2, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}