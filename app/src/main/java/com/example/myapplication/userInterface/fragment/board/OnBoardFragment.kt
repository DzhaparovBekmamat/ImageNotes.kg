package com.example.myapplication.userInterface.fragment.board

import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentOnBoardBinding
import com.example.myapplication.userInterface.sharedPreferences.app.Application

class OnBoardFragment :
    BaseFragment<FragmentOnBoardBinding>(FragmentOnBoardBinding::inflate),
    BoardAdapter.OpenListener {

    private lateinit var adapter: BoardAdapter

    override fun setUpUI() {
        adapter = BoardAdapter(this)
        binding.viewPager.adapter = adapter
        binding.wormDotsIndicator.setViewPager2(binding.viewPager)
        binding.skipButton.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            binding.viewPager.setCurrentItem(currentItem + 2, true)
        }
        binding.nextButton.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            binding.viewPager.setCurrentItem(currentItem + 1, true)
        }
    }

    override fun open() {
        Application.prefs.saveBoardState()
        findNavController().navigateUp()
    }
}
