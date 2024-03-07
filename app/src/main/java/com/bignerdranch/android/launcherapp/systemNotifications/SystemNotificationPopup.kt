package com.bignerdranch.android.launcherapp.systemNotifications

import android.content.res.ColorStateList
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bignerdranch.android.launcherapp.R
import com.bignerdranch.android.launcherapp.databinding.PopupSystemNotificationBinding
import java.util.LinkedList
import java.util.Queue
import java.util.Stack

internal class SystemNotificationPopup(
    view: View,
    private val timer: Long,
    private val type: SystemNotificationTypes,
    private val description: String,
    private val onActionClicked: (() -> Unit)?,
    private val onNotificationDismissed: () -> Unit,
) : PopupWindow() {
    companion object {
        private const val TAG = "+++SystemNotificationPopup"
    }

    private var countDownTimer: CountDownTimer? = null

    private val binding: PopupSystemNotificationBinding

    init {
        binding = PopupSystemNotificationBinding.inflate(LayoutInflater.from(view.context))
        contentView = binding.root
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.MATCH_PARENT
        animationStyle = R.style.PopupAnimation
        showAtLocation(view, Gravity.BOTTOM, 0, 0)
        setOnDismissListener {
            onNotificationDismissed.invoke()
        }

        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 500
        binding.root.startAnimation(alphaAnimation)
        setTexts()
        setColors()
        startLoading()
        setImage()
        clickListeners()
    }

    private fun setTexts() {
        binding.titleTxt.setText(type.title)
        binding.descriptionTxt.text = description
    }

    private fun setColors() {
        val color = ContextCompat.getColor(contentView.context, type.color)
        binding.titleTxt.setTextColor(color)
        binding.progressBar.setIndicatorColor(color)
        val colorStateList = ColorStateList.valueOf(color)
        binding.typeImv.backgroundTintList = colorStateList
    }

    private fun setImage() {
        binding.typeImv.setImageResource(type.iconRes)
    }

    private fun startLoading() {
        countDownTimer =
            object : CountDownTimer(timer, SystemNotificationBuilder.COUNTDOWN_TIMER_INTERVAL) {
                override fun onTick(millisUntilFinished: Long) {
                    val progress = 100 - (millisUntilFinished.toFloat() / timer * 100).toInt()
                    binding.progressBar.setProgress(progress, true)
                }

                override fun onFinish() {
                    dismiss()
                }
            }
        countDownTimer?.start()
    }

    private fun clickListeners() {
        if (onActionClicked == null) {
            binding.actionBtn.isVisible = false
        } else {
            binding.actionBtn.isVisible = true
            binding.actionBtn.setOnClickListener {
                countDownTimer?.cancel()
                countDownTimer = null
                onActionClicked.invoke()
                dismiss()
            }
        }
    }

}

class SystemNotificationBuilder private constructor() {
    companion object {
        const val MILLIS_IN_FUTURE = 20000L
        const val COUNTDOWN_TIMER_INTERVAL = 100L

        private var instance: SystemNotificationBuilder? = null

        fun getInstance(): SystemNotificationBuilder {
            if (instance == null) {
                instance = SystemNotificationBuilder()
            }

            return instance!!
        }
    }

    private val notifications = LinkedList<SystemNotificationItem>()
    private var showingNotifications = 0

    fun showNotification(notification: SystemNotificationItem) {
        notifications.add(notification)
        if (showingNotifications < 1) {

        }
    }
}

data class SystemNotificationItem(
    private val timer: Long = SystemNotificationBuilder.MILLIS_IN_FUTURE,
    private val type: SystemNotificationTypes,
    private val description: String,
    private val onActionClicked: (() -> Unit)? = null,
)