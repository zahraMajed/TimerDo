package com.example.timerdo.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class TasksEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id : Int = 0,
    @ColumnInfo(name = "Task_Name")
    val taskName: String,
    @ColumnInfo(name = "Task_Description")
    val taskDescription: String,
    @ColumnInfo(name = "Task_Time")
    var taskTime: String,
    @ColumnInfo(name = "Task_Status")
    var isTaskDone: Boolean
)