package com.example.recyclerviewineditmode.sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.recyclerviewineditmode.databinding.ItemImageBinding
import com.example.recyclerviewineditmode.databinding.ItemTextBinding
import com.example.recyclerviewineditmode.databinding.ItemTitleBinding
import com.example.recyclerviewineditmode.sample.items.ImageItem
import com.example.recyclerviewineditmode.sample.items.TeacherItem
import com.example.recyclerviewineditmode.sample.items.TitleItem

class TeachersAdapter: ListAdapter<TeacherItem, TeachersViewHolder>(TeachersDiffUtilsCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeachersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            TITLE_ITEM -> TeachersTitleHolder(ItemTitleBinding.inflate(layoutInflater, parent, false))
            IMAGE_ITEM -> TeachersImageHolder(ItemImageBinding.inflate(layoutInflater, parent, false))
            else -> TeachersTextHolder(ItemTextBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: TeachersViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when(item) {
            is TitleItem -> TITLE_ITEM
            is ImageItem -> IMAGE_ITEM
            else -> TEXT_ITEM
        }
    }

    companion object {
        const val TITLE_ITEM = 0
        const val IMAGE_ITEM = 1
        const val TEXT_ITEM = 2
    }
}