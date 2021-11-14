package com.example.timerdo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.timerdo.repository.Repository
import com.example.timerdo.roomDB.TasksDatabase
import com.example.timerdo.roomDB.TasksEntity
import kotlinx.coroutines.launch

class ViewModel (app: Application): AndroidViewModel(app) {
    private val repository:Repository=Repository(TasksDatabase.getInstance(app))

    fun getAllTasks(): List<TasksEntity> {
        return repository.getAllTasks()}
    fun insertTask(task: TasksEntity){
        viewModelScope.launch { repository.insertTask(task) }}
    fun updateTask(task: TasksEntity){
        viewModelScope.launch { repository.updateTask(task) }}
    fun updateTaskTime(taskID:Int,taskTime:String){
        viewModelScope.launch { repository.updateTaskTime(taskID,taskTime) }}
    fun updateTaskStatus(taskID:Int,isTaskDone:Boolean){
        viewModelScope.launch { repository.updateTaskStatus(taskID,isTaskDone) }}
    fun deleteTask(taskID: Int){
        viewModelScope.launch { repository.deleteTask(taskID) }}
    fun clearTaskTable(){
        viewModelScope.launch { repository.clearTaskTable() }}

}