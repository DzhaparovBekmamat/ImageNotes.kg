package com.example.myapplication.userInterface.fragment.board

import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentOnBoardBinding

class OnBoardFragment :
    BaseFragment<FragmentOnBoardBinding>(FragmentOnBoardBinding::inflate),
    BoardAdapter.OpenListener {

    private lateinit var adapter: BoardAdapter

    override fun setUpUI() {
        adapter = BoardAdapter(this)
        binding.viewPager.adapter = adapter
        binding.wormDotsIndicator.setViewPager2(binding.viewPager)

    }

    override fun open() {
        findNavController().navigate(R.id.addNoteFragment)
    }
}
