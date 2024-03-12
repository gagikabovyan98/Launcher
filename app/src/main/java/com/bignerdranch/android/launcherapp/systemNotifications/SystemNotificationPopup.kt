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

private class SystemNotificationPopup(
    view: View,
    private val timer: Long,
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

    private val animationDuration = 1000

    private fun startLoading() {
        countDownTimer =
            object : CountDownTimer(timer + animationDuration, SystemNotificationBuilder.COUNTDOWN_TIMER_INTERVAL) {
                override fun onTick(millisUntilFinished: Long) {
                    if (millisUntilFinished > animationDuration / 2 && millisUntilFinished < timer - animationDuration / 3) {
                        val progress = ((millisUntilFinished.toFloat() - animationDuration / 2) / timer * 100).toInt()
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
            binding.actionBtn.isVisible = false
        } else {
            binding.actionBtn.isVisible = true
            binding.actionBtn.setOnClickListener {
                object : CountDownTimer(10, 10) {
                    override fun onTick(millisUntilFinished: Long) {}

                    override fun onFinish() {
                        countDownTimer?.cancel()
                        countDownTimer = null
                        onActionClicked.invoke()
                        dismiss()
                    }
                }.start()
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

        fun getInstance(view: View): SystemNotificationBuilder {
            if (instance == null) {
                instance = SystemNotificationBuilder(view)
            }

            return instance!!
        }
    }

    private val notifications = LinkedList<SystemNotificationItem>()
    private var showingNotifications = 0
    private var topPopup: SystemNotificationPopup? = null
    private var bottomPopup: SystemNotificationPopup? = null

    fun addNotification(notification: SystemNotificationItem) {
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
                timer = notification.timer,
                description = notification.description,
                onActionClicked = notification.onActionClicked,
                onNotificationDismissed = {
                    if (it == bottomPopup) {
                        topPopup?.update(
                            0,
                            SystemNotificationPopup.BOTTOM_MARGIN,
                            topPopup!!.width,
                            topPopup!!.height
                        )
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
                topPopup!!.update(
                    0,
                    bottomPopup!!.getPopupHeight() + SystemNotificationPopup.BOTTOM_MARGIN * 2,
                    topPopup!!.width,
                    topPopup!!.height
                )
            }
        } else if (notifications.isEmpty()) {
            if (showingNotifications == 1) {
                topPopup?.update(
                    0,
                    SystemNotificationPopup.BOTTOM_MARGIN,
                    topPopup!!.width,
                    topPopup!!.height
                )
                bottomPopup = topPopup
                topPopup = null
            } else {
                bottomPopup = null
            }
        }
    }
}
