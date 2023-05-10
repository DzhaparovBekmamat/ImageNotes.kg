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
    private val imageList =
        listOf(R.drawable.notebookkkkk, R.drawable.notebookkkk, R.drawable.notebookkk)
    private val titleList = listOf("Совет №1", "Совет №2", "Совет №3")
    private val descriptionList = listOf("В этой жизни не важно, как ты падаешь. Важно, как ты поднимаешься.", "Для достижения счастья необходимы три слагаемых: мечта, вера в себя и трудолюбие.", "Люди не умеют жить. Их этому не учат.")

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
            binding.imageViewItemBoard.setImageResource(imageList[position])
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