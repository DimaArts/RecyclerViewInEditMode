package com.example.recyclerviewineditmode.sample.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewineditmode.sample.items.TeacherItem

abstract class TeachersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: TeacherItem)
}