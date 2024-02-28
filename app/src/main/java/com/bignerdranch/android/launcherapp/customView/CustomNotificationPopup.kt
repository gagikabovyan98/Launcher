package com.bignerdranch.android.launcherapp.customView

import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.PopupWindow
import com.bignerdranch.android.launcherapp.R
import com.bignerdranch.android.launcherapp.databinding.PopupLayoutBinding

class CustomNotificationPopup(
    view: View,
    private val timer: Long = MILLIS_IN_FUTURE,
    private val title: String = "",
    private val description: String = "",
    private val onSendClicked: () -> Unit = {}
) :
    PopupWindow() {
    companion object {
        private const val MILLIS_IN_FUTURE = 20000L
        private const val COUNTDOWN_TIMER_INTERVAL = 100L
        private const val TAG = "+++CustomNotificationPopup"
    }

    private var countDownTimer: CountDownTimer? = null

    private val binding: PopupLayoutBinding

    init {
        binding = PopupLayoutBinding.inflate(LayoutInflater.from(view.context))
        contentView = binding.root
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        animationStyle = R.style.PopupAnimation
        showAtLocation(view, Gravity.BOTTOM, 0, 0)

        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 500
        binding.root.startAnimation(alphaAnimation)
        setTexts()
        startLoading()
        clickListeners()
    }

    private fun setTexts() {
        binding.titleTxt.text = title
        binding.descriptionTxt.text = description
    }

    private fun startLoading() {
        countDownTimer = object : CountDownTimer(timer, COUNTDOWN_TIMER_INTERVAL) {
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
        binding.sendBtn.setOnClickListener {
            countDownTimer?.cancel()
            countDownTimer = null
            onSendClicked.invoke()
            dismiss()
        }
    }

}