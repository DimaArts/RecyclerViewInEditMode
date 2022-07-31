package com.example.recyclerviewineditmode.sample.adapter

import com.example.recyclerviewineditmode.databinding.ItemTitleBinding
import com.example.recyclerviewineditmode.sample.items.TeacherItem
import com.example.recyclerviewineditmode.sample.items.TitleItem

class TeachersTitleHolder(private val binding: ItemTitleBinding): TeachersViewHolder(binding.root) {
    override fun bind(item: TeacherItem) {
        val titleItem = item as? TitleItem
        binding.textView.text = titleItem?.text ?: ""
    }

}