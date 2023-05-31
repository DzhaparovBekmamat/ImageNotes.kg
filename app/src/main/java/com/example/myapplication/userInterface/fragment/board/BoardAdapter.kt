package com.example.myapplication.userInterface.fragment.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemBoardBinding

/**
 * Author: Dzhaparov Bekmamat
 */
class BoardAdapter(private val listener: OpenListener) :
    RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {
    private val imageList = listOf(R.raw.life, R.raw.man, R.raw.yoga)
    private val titleList = listOf("№1", "№2", "№3")
    private val descriptionList = listOf(
        "Удобно записывать свои идеи",
        "Создавать контрольные списки и зарисовки",
        "Помогает запомнить события"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BoardViewHolder(
        ItemBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = imageList.size

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class BoardViewHolder(private val binding: ItemBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            binding.lottieAnimation.setAnimation(imageList[position])
            binding.titleItemBoard.text = titleList[position]
            binding.descriptionItemBoard.text = descriptionList[position]
            binding.buttonItemBoard.setOnClickListener {
                listener.open()
            }

            if (position == imageList.size - 1) {
                binding.buttonItemBoard.visibility = View.VISIBLE
            } else {
                binding.buttonItemBoard.visibility = View.INVISIBLE
            }
        }
    }

    interface OpenListener {
        fun open()
    }
}