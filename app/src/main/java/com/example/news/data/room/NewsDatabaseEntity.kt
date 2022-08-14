package com.example.news.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsDatabaseEntity(

    @PrimaryKey()
    @ColumnInfo(name = "newsTitle")
    val newsTitle: String,

    @ColumnInfo(name = "newsDescription")
    val newsDescription: String?,

    @ColumnInfo(name = "newsImageUrl")
    val newsImageUrl: String? = "",

    @ColumnInfo(name = "newsDate")
    val newsDate: String?,

    @ColumnInfo(name = "newsIsFav")
    val newsIsFav: Boolean,

    )