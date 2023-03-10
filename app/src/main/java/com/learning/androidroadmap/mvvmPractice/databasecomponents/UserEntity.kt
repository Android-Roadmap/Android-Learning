package com.learning.androidroadmap.mvvmPractice.databasecomponents

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserDao.TABLE_NAME)
data class UserEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id : String,
    @ColumnInfo(name = "Title") val title : String,
    @ColumnInfo(name = "Body") val body : String
    )
