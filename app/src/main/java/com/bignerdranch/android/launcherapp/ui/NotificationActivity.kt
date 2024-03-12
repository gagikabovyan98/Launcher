package com.bignerdranch.android.launcherapp.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.bignerdranch.android.launcherapp.R
import com.bignerdranch.android.launcherapp.customView.CustomNotificationPopup
import com.bignerdranch.android.launcherapp.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private var isRectangleExpanded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.accordionVW.setOnClickListener {
            val scaleXAnimator = createScaleXAnimator()
            val rotateAnimator = createRotateAnimator()

            val animatorSet = AnimatorSet().apply {
                playTogether(scaleXAnimator, rotateAnimator)
                start()
            }

            if (isRectangleExpanded) toggleHintVisibility()
            animatorSet.addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    toggleHintVisibility()
                    isRectangleExpanded = !isRectangleExpanded
                }
            })
        }
    }

    private fun createScaleXAnimator(): ValueAnimator {

        val startWidth = binding.rectangleLayout.width.toFloat()
        val endWidth =
            if (isRectangleExpanded) 30f else resources.getDimensionPixelSize(R.dimen.rect_width).toFloat()

        val animator = ValueAnimator.ofFloat(startWidth, endWidth).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 1000
            addUpdateListener {
                val value = it.animatedValue as Float
                binding.rectangleLayout.layoutParams.width = value.toInt()
                binding.rectangleLayout.requestLayout()
            }
        }

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                toggleHintVisibility()
            }
        })

        return animator
    }

    private fun createRotateAnimator(): ObjectAnimator {
        val rotationAngle = if (isRectangleExpanded) 180f else 0f

        return ObjectAnimator.ofFloat(binding.accordionIMV, "rotation", rotationAngle).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 1000
        }
    }

    private fun toggleHintVisibility() {
        binding.hintTitleTV.visibility = if (isRectangleExpanded) View.INVISIBLE else View.VISIBLE
        binding.hintDescrTV.visibility = if (isRectangleExpanded) View.INVISIBLE else View.VISIBLE
        binding.lineVW.visibility = if (isRectangleExpanded) View.INVISIBLE else View.VISIBLE
    }

    private fun updateUIHintText(
        hintTitle: String,
        descrHint: String,
        titleColor: Int,
        descrColor: Int
    ) {
        binding.hintTitleTV.text = hintTitle
        binding.hintDescrTV.text = descrHint
        binding.hintTitleTV.setTextColor(getColor(titleColor))
        binding.hintDescrTV.setTextColor(getColor(descrColor))
    }

}
