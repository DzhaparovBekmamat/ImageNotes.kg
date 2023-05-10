package com.example.myapplication.userInterface.fragment.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.myapplication.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private var progressValue = 0
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        start()
    }

    private fun start() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (progressValue >= 1500) {
                startApp()
                finish()
            } else {
                start()
                progressValue += 100
                binding.progressBar.progress = progressValue
            }
        }, 100)
    }

    private fun startApp() {
        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        startActivity(intent)
    }
}