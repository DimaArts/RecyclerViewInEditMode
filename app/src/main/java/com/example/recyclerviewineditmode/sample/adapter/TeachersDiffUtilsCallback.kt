package com.example.recyclerviewineditmode.sample.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.recyclerviewineditmode.sample.items.ImageItem
import com.example.recyclerviewineditmode.sample.items.TeacherItem
import com.example.recyclerviewineditmode.sample.items.TextItem
import com.example.recyclerviewineditmode.sample.items.TitleItem

class TeachersDiffUtilsCallback : DiffUtil.ItemCallback<TeacherItem>() {
    override fun areItemsTheSame(oldItem: TeacherItem, newItem: TeacherItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TeacherItem, newItem: TeacherItem): Boolean =
        (oldItem is TextItem && newItem is TextItem && oldItem.text == newItem.text) ||
                (oldItem is TitleItem && newItem is TitleItem && oldItem.text == newItem.text) ||
                (oldItem is ImageItem && newItem is ImageItem && oldItem.url == newItem.url)
}