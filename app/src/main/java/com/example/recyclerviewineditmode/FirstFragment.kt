package com.example.recyclerviewineditmode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewineditmode.databinding.FragmentFirstBinding
import com.example.recyclerviewineditmode.sample.adapter.TeachersAdapter
import com.example.recyclerviewineditmode.sample.items.ImageItem
import com.example.recyclerviewineditmode.sample.items.TextItem
import com.example.recyclerviewineditmode.sample.items.TitleItem

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUrl = "https://picsum.photos/id/%s/200/300"
        val items = listOf(TitleItem(0, "John"),
            ImageItem(1, imageUrl),
            TextItem(2, "Sample text 1"),
            TitleItem(3, "Emma"),
            TextItem(4, "Sample text 2"),
            ImageItem(5, imageUrl),
            TextItem(6, "Sample text 3"),
            TextItem(7, "Sample text 4"),
            ImageItem(8, imageUrl),
            TextItem(9, "Sample text 5"),
            TitleItem(10, "James"),
            TextItem(11, "Sample text 6"),
            ImageItem(12, imageUrl),
            TitleItem(13, "Charlotte"),
            TextItem(14, "Sample text 7"),
            ImageItem(15, imageUrl),
            TextItem(16, "Sample text 8")
        )
        val adapter = TeachersAdapter()
        binding.recycler.adapter = adapter
        adapter.submitList(items)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}