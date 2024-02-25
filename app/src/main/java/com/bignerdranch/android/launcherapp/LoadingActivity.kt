package com.bignerdranch.android.launcherapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.launcherapp.databinding.ActivityLoadingBinding

private const val VK_URI: String = "https://vk.com/"
private const val TG_URI: String = "tg://resolve"
private const val TG_WEB_URI: String = "tg://resolve"


class LoadingActivity : AppCompatActivity() {

    private var _binding: ActivityLoadingBinding? = null
    private val binding: ActivityLoadingBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toTelegramIBT.setOnClickListener {
            openApp(TG_URI, TG_WEB_URI)
        }

        binding.toVkIBT.setOnClickListener {
            openApp(VK_URI, VK_URI)
        }
    }

    private fun openApp(appUri: String, webUri: String) {
        try {
            val uri = Uri.parse(appUri)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUri))
            startActivity(webIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}