package com.bignerdranch.android.launcherapp.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class LineView : View {
    private val linePaint = Paint()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        linePaint.color = 575656.toInt()
        linePaint.strokeWidth = 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val startX = (width * 0.1).toFloat()
        val endX = (width * 0.9).toFloat()
        val y = height.toFloat()
        canvas.drawLine(startX, y, endX, y, linePaint)
    }

}