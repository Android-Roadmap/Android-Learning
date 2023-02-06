package com.learning.androidroadmap.mvvmPractice.databasecomponents

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.learning.androidroadmap.R

@Dao
interface UserDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): List<UserEntity>

    @Insert(onConflict = IGNORE)
    fun insert(list: UserEntity)

    companion object{
        const val TABLE_NAME = "user_data"
    }
}