package com.bignerdranch.android.launcherapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.launcherapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.splashIV.setOnClickListener {
//            val intentLoading = Intent(this, LoadingActivity::class.java)
            val intentLoading = Intent(this, NotificationActivity::class.java)
            startActivity(intentLoading)
            finish()
        }
    }
}