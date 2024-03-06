package com.bignerdranch.android.launcherapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.launcherapp.databinding.ActivityNotificationBinding
import com.bignerdranch.android.launcherapp.systemNotifications.SystemNotificationPopup
import com.bignerdranch.android.launcherapp.systemNotifications.SystemNotificationTypes

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickListeners()
    }

    private fun clickListeners() {
        binding.showPopupBtn.setOnClickListener {
            showPopup(binding.root)
        }
    }

    private fun showPopup(view: View) {
        SystemNotificationPopup(
            view,
            type = SystemNotificationTypes.getRegularType(),
            description = "Уважаемые игроки! Через 15 минут произойдет плановый рестарт сервера!",
            onActionClicked = {}
        )
    }
}

