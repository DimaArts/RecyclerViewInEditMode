package com.example.recyclerviewineditmode.sample

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewineditmode.R
import com.example.recyclerviewineditmode.toolsrecyclerview.RecyclerViewDecoration

class TeachersMarginDecoration(context: Context): RecyclerViewDecoration(context) {
    private val titleTopMargin: Int = context.resources.getDimensionPixelSize(R.dimen.title_top_margin)
    private val titleBottomMargin: Int = context.resources.getDimensionPixelSize(R.dimen.title_bottom_margin)
    private val imageTopMargin: Int = context.resources.getDimensionPixelSize(R.dimen.image_top_margin)
    private val imageBottomMargin: Int = context.resources.getDimensionPixelSize(R.dimen.image_bottom_margin)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val isTitleView = isTitleView(view)
        val isImageView = isImageView(view)
        val isTextView = isTextView(view)
        if (isTitleView) {
            outRect.top = titleTopMargin
            outRect.bottom = titleBottomMargin
        }
        else if (isImageView) {
            outRect.top = imageTopMargin
            outRect.bottom = imageBottomMargin
        }
        else if (isTextView) {
            outRect.top = 0
            outRect.bottom = 0
        }
    }

    private fun isTitleView(view: View): Boolean {
        val rootView = getRootView(view)
        return rootView?.tag as? String == "title"
    }

    private fun isTextView(view: View): Boolean {
        val rootView = getRootView(view)
        return rootView?.tag as? String == "text"
    }

    private fun isImageView(view: View): Boolean {
        val rootView = getRootView(view)
        return rootView?.tag as? String == "image"
    }

    private fun getRootView(view: View): View? {
        return if (view.isInEditMode) (view as? ViewGroup)?.children?.firstOrNull() else view
    }
}