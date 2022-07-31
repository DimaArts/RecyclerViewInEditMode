package com.example.recyclerviewineditmode.dynamic

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout

class DynamicSizeLayout : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var maxWidth = 0
        var maxHeight = 0
        val widthSpecSize1 = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize1 = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(widthSpecSize1, heightSpecSize1)


        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val lp = child.layoutParams
            val widthSpec = if (lp.width == MATCH_PARENT) MeasureSpec.makeMeasureSpec(
                measuredWidth, MeasureSpec.EXACTLY
            )
            else MeasureSpec.makeMeasureSpec(
                0, MeasureSpec.UNSPECIFIED
            )
            val heightSpec = if (lp.height == MATCH_PARENT) MeasureSpec.makeMeasureSpec(
                measuredHeight, MeasureSpec.EXACTLY
            )
            else MeasureSpec.makeMeasureSpec(
                0, MeasureSpec.UNSPECIFIED
            )
            child.measure(widthSpec, heightSpec)
            val width = child.measuredWidth
            if (lp.width == MATCH_PARENT) {
                maxWidth = Int.MAX_VALUE
            } else if (width > maxWidth) {
                maxWidth = width
            }
            val height = child.measuredHeight
            if (lp.height == MATCH_PARENT) {
                maxHeight = Int.MAX_VALUE
            } else if (height > maxHeight) {
                maxHeight = height
            }
        }

        val widthSpecSize2 = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize2 = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(
            if (maxWidth == Int.MAX_VALUE) widthSpecSize2 else maxWidth,
            if (maxHeight == Int.MAX_VALUE) heightSpecSize2 else maxHeight
        )
    }
}