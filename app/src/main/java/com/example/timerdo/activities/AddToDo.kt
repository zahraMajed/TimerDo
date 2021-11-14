package com.example.timerdo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.timerdo.R
import com.example.timerdo.roomDB.TasksEntity
import com.example.timerdo.viewModel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddToDo : AppCompatActivity() {
    private lateinit var edTaskNameInsert: EditText
    private lateinit var edTaskDesInsert: EditText
    private lateinit var btnSaveInsert: Button
    lateinit var btnViewTaskInsert:Button
    private lateinit var backBtn: ImageView
    private lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)

        edTaskNameInsert=findViewById(R.id.edTaskNameInsert)
        edTaskDesInsert=findViewById(R.id.edTaskDesInsert)
        btnSaveInsert=findViewById(R.id.btnSaveInsert)
        btnViewTaskInsert=findViewById(R.id.btnViewTaskInsert)
        backBtn=findViewById(R.id.backAdd)
        viewModel= ViewModelProvider(this)[ViewModel::class.java]

        backBtn.setOnClickListener{
            if(!intent.extras!!.isEmpty){
                if(intent.getStringExtra("source")!!.equals("from Home")){
                    intent= Intent(this,HomePage::class.java)
                    startActivity(intent)
                }else if(intent.getStringExtra("source")!!.equals("from view")){
                    intent= Intent(this,ViewToDo::class.java)
                    startActivity(intent)
                }
            }
        }

        btnSaveInsert.setOnClickListener{
            if (edTaskNameInsert.text.isNotEmpty() && edTaskDesInsert.text.isNotEmpty()){ insertTask()
            }else Toast.makeText(applicationContext, "Please fill all entries!", Toast.LENGTH_SHORT).show()
        }

        btnViewTaskInsert.setOnClickListener{
            goToViewToDoActivity()
        }

    }

    private fun goToViewToDoActivity() {
        if(viewModel.getAllTasks().isNullOrEmpty()){
            Toast.makeText(applicationContext, "There is no task yet, add task first! ", Toast.LENGTH_SHORT).show()
        }else
        {
            intent= Intent(this,ViewToDo::class.java)
            startActivity(intent)
        }
    }

    private fun insertTask() {
        var taskName=edTaskNameInsert.text.toString(); var taskDes=edTaskDesInsert.text.toString()
        viewModel.insertTask(TasksEntity(0,taskName,taskDes,"",false))
        Toast.makeText(applicationContext, "Task saved successfully!", Toast.LENGTH_SHORT).show()
        edTaskNameInsert.text.clear(); edTaskDesInsert.text.clear()
    }
}