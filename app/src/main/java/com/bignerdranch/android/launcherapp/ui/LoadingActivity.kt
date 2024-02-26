package com.bignerdranch.android.launcherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.bignerdranch.android.launcherapp.common.Constants
import com.bignerdranch.android.launcherapp.databinding.ActivityLoadingBinding
import com.bignerdranch.android.launcherapp.utils.openTelegram
import com.bignerdranch.android.launcherapp.utils.openVK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoadingActivity : AppCompatActivity() {

    private var _binding: ActivityLoadingBinding? = null
    private val binding: ActivityLoadingBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickListeners()
        startLoading()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun clickListeners() {
        binding.toTelegramIBT.setOnBounceClickListener {
            openTelegram()
        }

        binding.toVkIBT.setOnBounceClickListener {
            openVK()
        }
    }

    private fun startLoading() {
        lifecycle.coroutineScope.launch(Dispatchers.IO) {
            for (i in 0..100) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.progress = i
                    binding.loadingInfo.text = Constants.textForLoading[(i / 10)]
                }
                delay(100)
            }
        }
    }

}