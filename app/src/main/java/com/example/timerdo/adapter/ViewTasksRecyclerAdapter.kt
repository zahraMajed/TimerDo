package com.example.timerdo.adapter

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.timerdo.R
import com.example.timerdo.activities.ViewToDo
import com.example.timerdo.roomDB.TasksEntity
import kotlinx.android.synthetic.main.task_item_view.view.*

class ViewTasksRecyclerAdapter  (private val activity: ViewToDo, private val taskList:List<TasksEntity>): RecyclerView.Adapter<ViewTasksRecyclerAdapter.TaskItemView>() {
    class TaskItemView(itemView: View):RecyclerView.ViewHolder(itemView)

    private var runningTask: Chronometer?=null
    private var oldTask:TasksEntity?=null
    private var oldTaskSandGlassAnim: LottieAnimationView?=null
    private var oldTaskStartIcon: ImageView?=null
    private var oldTaskStopIcon: ImageView?=null
    private var isCheckDone: Boolean=false
    private var isTimerRunning: Boolean=false
    private var pauseOffset: Long = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemView {
        return TaskItemView(LayoutInflater.from(parent.context).inflate(R.layout.task_item_view,parent,false))
    }

    override fun onBindViewHolder(holder: TaskItemView, position: Int) {
        val taskObj=taskList[position]
        val taskID=taskList[position].id
        val taskName=taskList[position].taskName
        val taskDescription=taskList[position].taskDescription
        val taskTime=taskList[position].taskTime
        val isTaskDone=taskList[position].isTaskDone

        holder.itemView.apply {
            tvTaskNameRV.text=taskName

            if(isTaskDone){taskStatusAnim.playAnimation()}

            taskStatusAnim.setOnClickListener{
                if (isCheckDone){
                    uncheckTaskStatusAnim(taskStatusAnim, taskID)}
                else{
                    checkTaskStatusAnim(taskStatusAnim, taskID)}
            }

            editIcon.setOnClickListener{
                showUpdateTaskSection(LL1RV,LL3RV,edUpdateTaskNameRV, edUpdateTaskDescriptionRV,taskName,taskDescription )
            }

            btnSaveUpdateRV.setOnClickListener{
                activity.updateTask(TasksEntity(taskID,edUpdateTaskNameRV.text.toString(), edUpdateTaskDescriptionRV.text.toString(),taskTime,isTaskDone))
            }

            colseUpdateIcon.setOnClickListener{
                hideUpdateTaskSection(LL1RV,LL3RV)
                activity.getAllTasks()
            }

            timerIcon.setOnClickListener{
                showTimerTaskSection(LL1RV,LL2RV,tvTaskNameTimer,tvTaskDescriptionTimer,taskName,taskDescription)}

            startIconTimer.setOnClickListener {
                  if (runningTask==null){ startPauseTask(chronometer,taskObj,startIconTimer,sandGlassAnim,stopIcon) }
                  else { if (runningTask==chronometer){ startPauseTask(chronometer,taskObj,startIconTimer,sandGlassAnim,stopIcon)}
                      else{ startAnotherTaskStopRunningTask(chronometer,taskObj,startIconTimer,sandGlassAnim,stopIcon) } }
            }

            stopIcon.setOnClickListener{
                stopTask(chronometer, taskObj,startIconTimer,stopIcon,sandGlassAnim ) }

            timerCloseIcon.setOnClickListener{
                hideTimerTaskSection(LL1RV,LL2RV)
                activity.getAllTasks() }


            if (activity.isBtnSummeryClick){
                showTaskSummarySection(editIcon,taskStatusAnim)
                if (taskTime.isNotEmpty()){ showTaskTime(tvTaskTimeRV,timerIcon,taskTime)}
                else{showTimerIconOnTaskSummary(tvTaskTimeRV,timerIcon)}
            }else { hideTaskSummarySection()}

            activity.backBtnSummary.setOnClickListener{ backToViewToDo() }
        }
    }

    override fun getItemCount(): Int= taskList.size

    private fun backToViewToDo(){
        activity.recreate()
    }

   private fun hideTaskSummarySection() {
       activity.btnSummaryTask.visibility=View.VISIBLE
       activity.btnAddTaskFromView.visibility=View.VISIBLE
       activity.tvActivityTitle.text="ToDo"
       activity.backBtnSummary.visibility=View.GONE
       activity.backBtnView.visibility=View.VISIBLE
    }

    private fun showTimerIconOnTaskSummary(tvTaskTimeRV: TextView, timerIcon: ImageView) {
        tvTaskTimeRV.visibility=View.GONE
        timerIcon.visibility=View.VISIBLE
    }

    private fun showTaskTime(tvTaskTimeRV: TextView, timerIcon: ImageView, taskTime: String) {
        tvTaskTimeRV.visibility=View.VISIBLE
        tvTaskTimeRV.text=taskTime
        timerIcon.visibility=View.GONE
    }

    private fun showTaskSummarySection(editIcon: ImageView, taskStatusAnim: LottieAnimationView) {
        activity.btnSummaryTask.visibility=View.GONE
        activity.btnAddTaskFromView.visibility=View.GONE
        activity.backBtnView.visibility=View.GONE
        editIcon.visibility=View.GONE
        taskStatusAnim.visibility=View.GONE
        activity.tvActivityTitle.text="Tasks Summary"
        activity.backBtnSummary.visibility=View.VISIBLE
    }

    private fun checkTaskStatusAnim(taskStatusAnim: LottieAnimationView, taskID: Int) {
        taskStatusAnim.speed= 1F
        taskStatusAnim.playAnimation()
        isCheckDone=true
        activity.updateTaskStatus(taskID,true)
    }

    private fun uncheckTaskStatusAnim(taskStatusAnim: LottieAnimationView, taskID:Int) {
        taskStatusAnim.speed= -1F
        taskStatusAnim.playAnimation()
        isCheckDone=false
        activity.updateTaskStatus(taskID,false)
    }

    private fun hideTimerTaskSection(lL1RV: LinearLayout, lL2RV: ConstraintLayout) {
        lL1RV.visibility=View.VISIBLE
        lL2RV.visibility=View.GONE
    }

    private fun showTimerTaskSection(lL1RV: LinearLayout, lL2RV: ConstraintLayout, tvTaskNameTimer: TextView, tvTaskDescriptionTimer: TextView, taskName: String, taskDescription: String) {
        lL1RV.visibility=View.GONE
        lL2RV.visibility=View.VISIBLE
        tvTaskNameTimer.text=taskName
        tvTaskDescriptionTimer.text=taskDescription
    }

    private fun hideUpdateTaskSection(lL1RV: LinearLayout, lL3RV: ConstraintLayout) {
        lL1RV.visibility=View.VISIBLE
        lL3RV.visibility=View.GONE
    }

    private fun showUpdateTaskSection(lL1RV: LinearLayout, lL3RV: ConstraintLayout,
        edUpdateTaskNameRV: EditText, edUpdateTaskDescriptionRV: EditText,
        taskName: String, taskDescription: String) {
        lL1RV.visibility=View.GONE
        lL3RV.visibility=View.VISIBLE
        edUpdateTaskNameRV.setText(taskName)
        edUpdateTaskDescriptionRV.setText(taskDescription)
    }


    private fun startPauseTask(
        chronometer: Chronometer,
        taskObj: TasksEntity,
        startIcon: ImageView,
        sandGlassAnim: LottieAnimationView,
        stopIcon: ImageView
    ){
        if (!isTimerRunning){
            //getChronometerStart()
            chronometer.base= SystemClock.elapsedRealtime() - pauseOffset
            chronometer.start()
            //updateIsTimerRunningAndOldTaskVariables()
            isTimerRunning=true
            runningTask=chronometer
            oldTask=taskObj
            oldTaskStartIcon=startIcon
            oldTaskStopIcon=stopIcon
            oldTaskSandGlassAnim=sandGlassAnim
            //getTimeRunningMode()
            sandGlassAnim.playAnimation()
            startIcon.background= ContextCompat.getDrawable(activity, R.drawable.pause_circle_filled)
            stopIcon.visibility=View.VISIBLE
        }else{
            chronometer.stop()
            isTimerRunning=false
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
            startIcon.background= ContextCompat.getDrawable(activity, R.drawable.not_started)
            stopIcon.visibility=View.GONE
            sandGlassAnim.pauseAnimation()
            taskObj.taskTime=chronometer.text.toString()
            //viewModel.updateTaskTime(taskObj.id,taskObj.taskTime)
            activity.updateTaskTime(taskObj.id,taskObj.taskTime)
        }
    }

    private fun stopTask(
        chronometer: Chronometer, taskObj: TasksEntity,
        startIconTimer: ImageView, stopIcon: ImageView, sandGlassAnim: LottieAnimationView
    ){
        chronometer.stop()
        isTimerRunning=false
        taskObj.taskTime=chronometer.text.toString()
        activity.updateTaskTime(taskObj.id,taskObj.taskTime)
        chronometer.base= SystemClock.elapsedRealtime()
        pauseOffset=0
        startIconTimer.background= ContextCompat.getDrawable(activity, R.drawable.not_started)
        stopIcon.visibility=View.GONE
        sandGlassAnim.pauseAnimation()
    }

    private fun startAnotherTaskStopRunningTask(
        chronometer: Chronometer, taskObj: TasksEntity, startIcon: ImageView,
        sandGlassAnim: LottieAnimationView, stopIcon: ImageView){
        runningTask?.stop()
        oldTask!!.taskTime=runningTask!!.text.toString()
        oldTaskStartIcon!!.background= ContextCompat.getDrawable(activity, R.drawable.pause_circle_filled)
        oldTaskStopIcon!!.visibility=View.VISIBLE
        oldTaskSandGlassAnim!!.pauseAnimation()
        activity.updateTaskTime(oldTask!!.id,oldTask!!.taskTime)

        chronometer.base= SystemClock.elapsedRealtime() - 0
        chronometer.start()

        startIcon.background= ContextCompat.getDrawable(activity, R.drawable.pause_circle_filled)
        stopIcon.visibility=View.VISIBLE
        sandGlassAnim.playAnimation()

        oldTask=taskObj
        runningTask=chronometer
        oldTaskStartIcon=startIcon
        oldTaskStopIcon=stopIcon
        oldTaskSandGlassAnim=sandGlassAnim
    }
}