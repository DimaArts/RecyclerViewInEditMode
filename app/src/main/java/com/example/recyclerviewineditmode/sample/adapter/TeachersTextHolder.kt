package com.example.recyclerviewineditmode.sample.adapter

import com.example.recyclerviewineditmode.databinding.ItemTextBinding
import com.example.recyclerviewineditmode.sample.items.TeacherItem
import com.example.recyclerviewineditmode.sample.items.TextItem

class TeachersTextHolder(private val binding: ItemTextBinding): TeachersViewHolder(binding.root) {
    override fun bind(item: TeacherItem) {
        val textItem = item as? TextItem
        binding.textView.text = textItem?.text ?: ""
    }

}