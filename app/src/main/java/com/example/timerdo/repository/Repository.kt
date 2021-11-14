package com.example.timerdo.repository

import androidx.lifecycle.LiveData
import com.example.timerdo.roomDB.TasksDatabase
import com.example.timerdo.roomDB.TasksEntity

class Repository (private val tasksDatabase: TasksDatabase) {

    fun getAllTasks(): List<TasksEntity> {
        return tasksDatabase.getTasksDao().getAllTasks()}
    suspend fun insertTask(task: TasksEntity){
        tasksDatabase.getTasksDao().insertTask(task) }
    suspend fun updateTask(task: TasksEntity){
        tasksDatabase.getTasksDao().updateTask(task)}
    suspend fun updateTaskTime(taskID:Int,taskTime:String){
        tasksDatabase.getTasksDao().updateTaskTime(taskID,taskTime)}
    suspend fun updateTaskStatus(taskID:Int,isTaskDone:Boolean){
        tasksDatabase.getTasksDao().updateTaskStatus(taskID,isTaskDone) }
    suspend fun deleteTask(taskID: Int){
        tasksDatabase.getTasksDao().deleteTask(taskID) }
    suspend fun clearTaskTable(){
        tasksDatabase.getTasksDao().clearTaskTable()
    }

}