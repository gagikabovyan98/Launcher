package com.bignerdranch.android.launcherapp.customView

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView


private const val DURATION: Long = 100

class BounceImage @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val resetAnimatorSet = AnimatorSet()
                resetAnimatorSet.apply {
                    playTogether(
                        ObjectAnimator.ofFloat(this@BounceImage, "scaleX", 0.7f),
                        ObjectAnimator.ofFloat(this@BounceImage, "scaleY", 0.7f)
                    )
                    interpolator = DecelerateInterpolator()
                    duration = DURATION
                    start()
                }
            }

            MotionEvent.ACTION_UP -> {
                val resetAnimatorSet = AnimatorSet()
                resetAnimatorSet.apply {
                    playTogether(
                        ObjectAnimator.ofFloat(this@BounceImage, "scaleX", 1f),
                        ObjectAnimator.ofFloat(this@BounceImage, "scaleY", 1f)
                    )
                    interpolator = DecelerateInterpolator()
                    duration = DURATION / 2
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            performClick()
                        }
                    })
                    start()
                }
            }
        }
        return true
    }
}

class BounceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val resetAnimatorSet = AnimatorSet()
                resetAnimatorSet.apply {
                    playTogether(
                        ObjectAnimator.ofFloat(this@BounceView, "scaleX", 0.7f),
                        ObjectAnimator.ofFloat(this@BounceView, "scaleY", 0.7f)
                    )
                    interpolator = DecelerateInterpolator()
                    duration = 200
                    start()
                }
            }

            MotionEvent.ACTION_UP -> {
                val resetAnimatorSet = AnimatorSet()
                resetAnimatorSet.apply {
                    playTogether(
                        ObjectAnimator.ofFloat(this@BounceView, "scaleX", 1f),
                        ObjectAnimator.ofFloat(this@BounceView, "scaleY", 1f)
                    )
                    interpolator = DecelerateInterpolator()
                    duration = 200
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            performClick()
                        }
                    })
                    start()
                }
            }

        }
        return true

    }
}

class BounceFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val TAG = "+++BounceImage"
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val resetAnimatorSet = AnimatorSet()
                resetAnimatorSet.apply {
                    playTogether(
                        ObjectAnimator.ofFloat(this@BounceFrameLayout, "scaleX", 0.7f),
                        ObjectAnimator.ofFloat(this@BounceFrameLayout, "scaleY", 0.7f)
                    )
                    interpolator = DecelerateInterpolator()
                    duration = 200
                    start()
                }
            }

            MotionEvent.ACTION_UP -> {
                val resetAnimatorSet = AnimatorSet()
                resetAnimatorSet.apply {
                    playTogether(
                        ObjectAnimator.ofFloat(this@BounceFrameLayout, "scaleX", 1f),
                        ObjectAnimator.ofFloat(this@BounceFrameLayout, "scaleY", 1f)
                    )
                    interpolator = DecelerateInterpolator()
                    duration = 200
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            performClick()
                        }
                    })
                    start()
                }
            }

        }
        return true

    }
}

private interface SimpleAnimationListener : Animation.AnimationListener {
    override fun onAnimationStart(animation: Animation?) {}

    override fun onAnimationRepeat(animation: Animation?) {}
}