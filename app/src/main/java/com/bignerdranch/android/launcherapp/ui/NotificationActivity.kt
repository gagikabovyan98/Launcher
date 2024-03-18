package com.bignerdranch.android.launcherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.launcherapp.databinding.ActivityNotificationBinding
import com.bignerdranch.android.launcherapp.systemNotifications.SystemNotificationBuilder
import com.bignerdranch.android.launcherapp.systemNotifications.SystemNotificationItem
import com.bignerdranch.android.launcherapp.systemNotifications.SystemNotificationTypes

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var systemNotificationBuilder: SystemNotificationBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        systemNotificationBuilder = SystemNotificationBuilder.getInstance(binding.root)
        clickListeners()
    }

    private fun clickListeners() {
        binding.showPopupButton.setOnClickListener {
            showPopup()
        }
    }

    private fun showPopup() {
        systemNotificationBuilder.showNotification(
            notification = SystemNotificationItem(
                type = SystemNotificationTypes.getRegularType(),
                description = "Уважаемые игроки! Через 15 минут произойдет плановый рестарт сервера!",
                onActionClicked = {}
            )
        )
    }
}

