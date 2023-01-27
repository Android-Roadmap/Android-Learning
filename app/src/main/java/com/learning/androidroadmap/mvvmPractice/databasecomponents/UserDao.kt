package com.learning.androidroadmap.mvvmPractice.databasecomponents

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user_data")
    fun getAll(): List<UserEntity>

    @Insert(onConflict = IGNORE)
    fun insert(list: UserEntity)

}