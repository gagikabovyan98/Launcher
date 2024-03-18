package com.bignerdranch.android.launcherapp.systemNotifications

import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bignerdranch.android.launcherapp.R
import com.bignerdranch.android.launcherapp.databinding.PopupSystemNotificationBinding
import java.util.LinkedList

private class SystemNotificationPopup(
    view: View,
    private val timeToDismiss: Long,
    private val type: SystemNotificationTypes,
    private val description: String,
    private val onActionClicked: (() -> Unit)?,
    private val onNotificationDismissed: (SystemNotificationPopup) -> Unit,
) : PopupWindow() {
    companion object {
        private const val TAG = "+++SystemNotificationPopup"
        const val BOTTOM_MARGIN = 30
    }

    private var countDownTimer: CountDownTimer? = null

    private val binding: PopupSystemNotificationBinding

    init {
        binding = PopupSystemNotificationBinding.inflate(LayoutInflater.from(view.context))
        contentView = binding.root
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.MATCH_PARENT
        animationStyle = R.style.PopupAnimation
        showAtLocation(view, Gravity.BOTTOM, 0, BOTTOM_MARGIN)
        setOnDismissListener {
            onNotificationDismissed.invoke(this)
        }

        setTexts()
        setColors()
        startLoading()
        setImage()
        clickListeners()
    }

    private fun setTexts() {
        binding.titleTextView.setText(type.title)
        binding.descriptionTextView.text = description
    }

    private fun setColors() {
        val color = ContextCompat.getColor(contentView.context, type.color)
        binding.titleTextView.setTextColor(color)
        binding.progressBar.setIndicatorColor(color)
    }

    private fun setImage() {
        binding.typeImageView.setImageResource(type.iconRes)
    }

    private val animationDuration = 1000

    private fun startLoading() {
        countDownTimer =
            object : CountDownTimer(timeToDismiss + animationDuration, SystemNotificationBuilder.COUNTDOWN_TIMER_INTERVAL) {
                override fun onTick(millisUntilFinished: Long) {
                    if (millisUntilFinished > animationDuration / 2 && millisUntilFinished < timeToDismiss - animationDuration / 3) {
                        val progress = ((millisUntilFinished.toFloat() - animationDuration / 2) / timeToDismiss * 100).toInt()
                        binding.progressBar.setProgress(progress, true)
                    }
                }

                override fun onFinish() {
                    dismiss()
                }
            }
        countDownTimer?.start()
    }

    private fun clickListeners() {
        if (onActionClicked == null || !type.isAction()) {
            binding.actionButton.isVisible = false
        } else {
            binding.actionButton.isVisible = true
            binding.actionButton.setOnClickListener {
                countDownTimer?.cancel()
                countDownTimer = null
                onActionClicked.invoke()
                dismiss()
            }
        }

        binding.textContainer.setOnClickListener {
            countDownTimer?.cancel()
            countDownTimer = null
            dismiss()
        }
    }

    fun getPopupHeight() = binding.root.measuredHeight
}

class SystemNotificationBuilder private constructor(private val view: View) {
    companion object {
        private const val TAG = "+++SystemNotificationBuilder"
        const val MILLIS_IN_FUTURE = 5000L
        const val COUNTDOWN_TIMER_INTERVAL = 100L

        private var instance: SystemNotificationBuilder? = null

        fun getInstance(view: View) = instance ?: SystemNotificationBuilder(view).also {
            instance = it
        }

    }

    private val notifications = LinkedList<SystemNotificationItem>()
    private var showingNotifications = 0
    private var topPopup: SystemNotificationPopup? = null
    private var bottomPopup: SystemNotificationPopup? = null

    fun showNotification(notification: SystemNotificationItem) {
        notifications.add(notification)
        showNotification()
    }

    private fun showNotification() {
        if (notifications.isNotEmpty() && showingNotifications < 2) {
            val notification = notifications.poll() ?: return
            showingNotifications++
            topPopup = SystemNotificationPopup(
                view,
                type = notification.type,
                timeToDismiss = notification.timer,
                description = notification.description,
                onActionClicked = notification.onActionClicked,
                onNotificationDismissed = { systemNotificationPopup ->
                    if (systemNotificationPopup == bottomPopup) {
                        topPopup?.let {
                            it.update(
                                0,
                                SystemNotificationPopup.BOTTOM_MARGIN,
                                it.width,
                                it.height
                            )
                        }

                        bottomPopup = topPopup
                    } else {
                        topPopup = null
                    }
                    showingNotifications--
                    showNotification()
                }
            )

            if (bottomPopup == null) {
                bottomPopup = topPopup
                topPopup = null
            } else {
                topPopup?.let {
                    it.update(
                        0,
                        bottomPopup!!.getPopupHeight() + SystemNotificationPopup.BOTTOM_MARGIN * 2,
                        it.width,
                        it.height
                    )
                }
            }
        } else if (notifications.isEmpty()) {
            if (showingNotifications == 1) {
                topPopup?.let {
                    it.update(
                        0,
                        SystemNotificationPopup.BOTTOM_MARGIN,
                        it.width,
                        it.height
                    )
                }
                bottomPopup = topPopup
                topPopup = null
            } else {
                bottomPopup = null
            }
        }
    }
}
