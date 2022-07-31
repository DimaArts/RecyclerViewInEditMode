package com.example.recyclerviewineditmode.sample.adapter

import com.example.recyclerviewineditmode.databinding.ItemImageBinding
import com.example.recyclerviewineditmode.sample.items.ImageItem
import com.example.recyclerviewineditmode.sample.items.TeacherItem
import com.squareup.picasso.Picasso

class TeachersImageHolder(private val binding: ItemImageBinding): TeachersViewHolder(binding.root) {
    override fun bind(item: TeacherItem) {
        val imageItem = item as? ImageItem
        Picasso.get().load(String.format(imageItem?.url ?: "", imageItem?.id?.toString()))
            .into(binding.image)
    }

}