package com.bignerdranch.android.launcherapp.customView

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView
import com.bignerdranch.android.launcherapp.R


class BounceImage @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var bounceClickListener: OnClickListener? = null

    init {
        setOnClickListener {
            if (bounceClickListener != null) {
                startAnimation {
                    bounceClickListener!!.onClick(this)
                }
            }
        }
    }

    fun setOnBounceClickListener(l: OnClickListener?) {
        bounceClickListener = l
    }

    private fun startAnimation(animationDone: () -> Unit) {
        val scaleDownAnim = AnimationUtils.loadAnimation(context, R.anim.scale_down)
        val scaleUpAnim = AnimationUtils.loadAnimation(context, R.anim.scale_up)

        scaleDownAnim.setAnimationListener(object : SimpleAnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                scaleUpAnim.setAnimationListener(object : SimpleAnimationListener {
                    override fun onAnimationEnd(animation: Animation?) {
                        animationDone.invoke()
                    }
                })
                this@BounceImage.startAnimation(scaleUpAnim)
            }
        })

        this.startAnimation(scaleDownAnim)
    }
}

private interface SimpleAnimationListener : Animation.AnimationListener {
    override fun onAnimationStart(animation: Animation?) {}

    override fun onAnimationRepeat(animation: Animation?) {}
}