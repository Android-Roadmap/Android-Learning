package com.learning.androidroadmap.mvvmPractice.databasecomponents

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object{
        private var DATABASE_INSTANCE : UserDatabase? = null

        fun getDatabase(context: Context) : UserDatabase{
            val temporaryInstance = DATABASE_INSTANCE
            if(temporaryInstance != null){
                return temporaryInstance
            }
            synchronized(this){
                val databaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                DATABASE_INSTANCE = databaseInstance
                return databaseInstance
            }
        }
    }
}