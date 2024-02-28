package com.bignerdranch.android.launcherapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.launcherapp.customView.CustomNotificationPopup
import com.bignerdranch.android.launcherapp.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.showPopupBtn.setOnClickListener {
            showPopup(binding.root)
        }
    }

    private fun showPopup(view: View) {
        CustomNotificationPopup(
            view,
            title = "Системные уведомления",
            description = "Здравствуйте! В городе введен режим",
        )
    }
}

