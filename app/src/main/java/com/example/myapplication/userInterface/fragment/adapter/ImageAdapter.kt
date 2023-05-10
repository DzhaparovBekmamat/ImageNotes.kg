package com.example.myapplication.userInterface.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.NoteListBinding

class ImageAdapter(private val onClick: (position: Int) -> Unit) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private val list = mutableListOf<Int>()

    fun setList(imageList: List<Int>) {
        list.clear()
        list.addAll(imageList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NoteListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = list[position]
        holder.bind(image)
        holder.itemView.setOnClickListener {
            onClick(list[position])
            holder.itemView.alpha = 0.5f
        }
    }

    inner class ViewHolder(private val binding: NoteListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            Glide.with(binding.imageViewNoteList)
                .load(position)
                .into(binding.imageViewNoteList)
        }
    }
}