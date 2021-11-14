package com.example.timerdo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timerdo.R
import com.example.timerdo.adapter.ViewTasksRecyclerAdapter
import com.example.timerdo.viewModel.ViewModel
import kotlinx.android.synthetic.main.activity_view_to_do.*

class HomePage : AppCompatActivity() {
    private lateinit var btnViewTaskMain: Button
    private lateinit var btnInsertTaskMain: Button
    private lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        viewModel= ViewModelProvider(this)[ViewModel::class.java]
        btnViewTaskMain=findViewById(R.id.btnViewTaskMain)
        btnInsertTaskMain=findViewById(R.id.btnInsertTaskMain)
        btnInsertTaskMain.setOnClickListener{ goAddToDoActivity() }
        btnViewTaskMain.setOnClickListener{ goViewToDoActivity() }
    }

    private fun goAddToDoActivity() {
        intent= Intent(this, AddToDo::class.java)
        intent.putExtra("source","from Home")
        startActivity(intent)
    }
    private fun goViewToDoActivity() {
        if(viewModel.getAllTasks().isNullOrEmpty()){
            Toast.makeText(applicationContext, "There is no task yet, add task first! ", Toast.LENGTH_SHORT).show()
        }else
        {
            intent= Intent(this, ViewToDo::class.java)
            startActivity(intent)
        }
    }
}