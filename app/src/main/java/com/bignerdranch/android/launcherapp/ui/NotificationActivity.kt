package com.bignerdranch.android.launcherapp.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bignerdranch.android.launcherapp.customView.CustomNotificationPopup
import com.bignerdranch.android.launcherapp.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private var isRectangleExpanded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButton.setOnClickListener {
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

    private fun updateUIHintText(hintTitle: String, descrHint: String, titleColor: Int, descrColor: Int) {
        binding.hintTitleTV.text = hintTitle
        binding.hintDescrTV.text = descrHint
        binding.hintTitleTV.setTextColor(getColor(titleColor))
        binding.hintDescrTV.setTextColor(getColor(descrColor))
    }

    private fun createScaleXAnimator(): ValueAnimator {
        val startScaleX = if (isRectangleExpanded) 1f else 0.25f
        val endScaleX = if (isRectangleExpanded) 0.25f else 1f

        val pivotX = 1f

        return ValueAnimator.ofFloat(startScaleX, endScaleX).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 500
            addUpdateListener {
                val value = it.animatedValue as Float
                binding.rectangleLayout.scaleX = value
                binding.rectangleLayout.pivotX = binding.rectangleLayout.width * pivotX

                // Обновляем позицию кнопки в соответствии с изменяющимся размером квадрата
                val layoutParams = binding.imageButton.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.marginStart = ((1 - value) * 30).toInt() // Это только пример, вам может потребоваться другой способ рассчета положения
                binding.imageButton.layoutParams = layoutParams
            }
        }
    }

    private fun createRotateAnimator(): ObjectAnimator {
        val rotationAngle = if (isRectangleExpanded) 0f else 180f

        return ObjectAnimator.ofFloat(binding.imageButton, "rotation", rotationAngle).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 500
        }
    }

    private fun toggleHintVisibility() {
        binding.hintTitleTV.visibility = if (isRectangleExpanded) View.INVISIBLE else View.VISIBLE
        binding.hintDescrTV.visibility = if (isRectangleExpanded) View.INVISIBLE else View.VISIBLE
    }

    private fun showPopup(view: View) {
        CustomNotificationPopup(
            view,
            title = "Системные уведомления",
            description = "Здравствуйте! В городе введен режим",
        )
    }
}
