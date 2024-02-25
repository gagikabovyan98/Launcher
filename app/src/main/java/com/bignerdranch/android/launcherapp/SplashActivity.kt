package com.bignerdranch.android.launcherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.launcherapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding: ActivitySplashBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.splashIV.setOnClickListener {
            val intentLoading: Intent = Intent(this, LoadingActivity::class.java)
            startActivity(intentLoading)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}