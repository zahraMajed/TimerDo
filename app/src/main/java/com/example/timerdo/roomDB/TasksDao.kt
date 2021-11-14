package com.example.timerdo.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TasksDao {
    @Query("SELECT * FROM Tasks")
    fun getAllTasks(): List<TasksEntity>
    @Query("UPDATE Tasks SET Task_Time=:taskTime  WHERE ID=:taskID")
    fun updateTaskTime(taskID:Int,taskTime:String)
    @Query("UPDATE Tasks SET Task_Status=:isTaskDone  WHERE ID=:taskID")
    fun updateTaskStatus(taskID:Int,isTaskDone:Boolean)
    @Query("DELETE FROM Tasks WHERE ID=:taskID ")
    fun deleteTask(taskID: Int)
    @Query("DELETE FROM Tasks")
    fun clearTaskTable()
    @Insert
    fun insertTask(task:TasksEntity)
    @Update
    fun updateTask(task: TasksEntity)
}