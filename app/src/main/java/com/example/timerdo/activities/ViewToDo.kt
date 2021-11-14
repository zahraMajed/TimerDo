package com.example.timerdo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timerdo.R
import com.example.timerdo.adapter.ViewTasksRecyclerAdapter
import com.example.timerdo.roomDB.TasksEntity
import com.example.timerdo.viewModel.ViewModel
import kotlinx.android.synthetic.main.activity_view_to_do.*

class ViewToDo : AppCompatActivity() {
    lateinit var btnSummaryTask: Button
    lateinit var tvActivityTitle: TextView
    lateinit var backBtnView:ImageView
    lateinit var backBtnSummary:ImageView
    lateinit var btnAddTaskFromView:ImageView
    var isBtnSummeryClick=false
    private lateinit var viewModel: ViewModel
    private lateinit var taskList: List<TasksEntity>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_to_do)
        btnSummaryTask=findViewById(R.id.btnSummaryTask)
        btnAddTaskFromView=findViewById(R.id.btnAddTaskFromView)
        tvActivityTitle=findViewById(R.id.tvActivityTitle)
        backBtnView=findViewById(R.id.backBtnView)
        backBtnSummary=findViewById(R.id.backBtnSummary)
        viewModel= ViewModelProvider(this)[ViewModel::class.java]
        taskList= listOf()
        getAllTasks()
        btnSummaryTask.setOnClickListener{
            isBtnSummeryClick=true
            backBtnView.visibility= View.GONE
            getAllTasks()
        }
        backBtnView.setOnClickListener{
            intent= Intent(this,HomePage::class.java)
            startActivity(intent)
        }
        btnAddTaskFromView.setOnClickListener{
            intent= Intent(this,AddToDo::class.java)
            intent.putExtra("source","from view")
            startActivity(intent)
        }
    }


    fun getAllTasks() {
        if(viewModel.getAllTasks().isNullOrEmpty()){
            Toast.makeText(applicationContext, "There is no task yet, add task first! ", Toast.LENGTH_SHORT).show()
        }else {
            taskList=viewModel.getAllTasks()
            rv_main.adapter=ViewTasksRecyclerAdapter(this,taskList)
            rv_main.layoutManager=LinearLayoutManager(this)}
    }

    fun updateTask(task:TasksEntity){
        viewModel.updateTask(task)
        Toast.makeText(applicationContext, "${task.taskName} is up-to-date!", Toast.LENGTH_SHORT).show()
    }
    fun updateTaskTime(taskID:Int, taskTime:String){
        viewModel.updateTaskTime(taskID,taskTime)
    }
    fun updateTaskStatus(taskID:Int, isTaskDone:Boolean){
        viewModel.updateTaskStatus(taskID,isTaskDone)
    }
}