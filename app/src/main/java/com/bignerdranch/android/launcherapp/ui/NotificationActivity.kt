package com.bignerdranch.android.launcherapp.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
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

            AnimatorSet().apply {
                playTogether(scaleXAnimator, rotateAnimator)
                start()
            }

            toggleHintVisibility()
            isRectangleExpanded = !isRectangleExpanded
        }
    }

    private fun createScaleXAnimator(): ValueAnimator {
        val startScaleX = if (isRectangleExpanded) 1f else 0.05f
        val endScaleX = if (isRectangleExpanded) 0.05f else 1f
        val pivotX = 1f

        val animator = ValueAnimator.ofFloat(startScaleX, endScaleX).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 1000
            addUpdateListener {
                val value = it.animatedValue as Float
                binding.rectangleLayout.scaleX = value
                binding.rectangleLayout.pivotX = binding.rectangleLayout.width * pivotX

                val translationX = (1 - value) * binding.rectangleLayout.width
                binding.accordionVW.translationX = translationX
            }
        }

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

    private fun updateUIHintText(hintTitle: String, descrHint: String, titleColor: Int, descrColor: Int) {
        binding.hintTitleTV.text = hintTitle
        binding.hintDescrTV.text = descrHint
        binding.hintTitleTV.setTextColor(getColor(titleColor))
        binding.hintDescrTV.setTextColor(getColor(descrColor))
    }

}
