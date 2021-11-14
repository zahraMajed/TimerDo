package com.example.timerdo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.example.timerdo.activities.HomePage
import com.example.timerdo.viewModel.ViewModel

class SplashScreen : AppCompatActivity() {
    private lateinit var handler: Handler
    private lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_splash)
        viewModel= ViewModelProvider(this)[ViewModel::class.java]
        handler= Handler()
        handler.postDelayed({
            intent= Intent(this,HomePage::class.java)
            startActivity(intent)
            finish() }, 6600)
        clearDB()
    }

    private fun clearDB() {
       viewModel.clearTaskTable()
    }
}