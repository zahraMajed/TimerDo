package com.example.timerdo.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities= [TasksEntity::class], version = 2, exportSchema = false)
abstract class TasksDatabase : RoomDatabase() {
    companion object{
        var instance:TasksDatabase?=null
        fun getInstance(context: Context):TasksDatabase {
            if(instance!=null){ return instance as TasksDatabase}
            instance= Room.databaseBuilder(context,TasksDatabase::class.java, "data").run {allowMainThreadQueries()}
                .fallbackToDestructiveMigration()
                .build()
            return instance as TasksDatabase
        } }
    abstract fun getTasksDao():TasksDao
}