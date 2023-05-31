package com.example.myapplication.userInterface.fragment.board

import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentOnBoardBinding
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("DEPRECATION")
class OnBoardFragment : BaseFragment<FragmentOnBoardBinding>(FragmentOnBoardBinding::inflate),
    BoardAdapter.OpenListener {
    private lateinit var adapter: BoardAdapter

    override fun setUpUI() {
        adapter = BoardAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.wormDotsIndicator, binding.viewPager) { tab, _ ->
            tab.setIcon(R.drawable.tab_indicator)
        }.attach()
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
        findNavController().navigateUp()
        findNavController().navigate(R.id.phoneFragment)
    }
}
