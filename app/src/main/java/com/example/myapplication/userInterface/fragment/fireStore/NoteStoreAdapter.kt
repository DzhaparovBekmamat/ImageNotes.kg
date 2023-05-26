package com.example.myapplication.userInterface.fragment.fireStore

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.databinding.NoteListBinding

/**
 * Author: Dzhaparov Bekmamat
 */
class NoteStoreAdapter : RecyclerView.Adapter<NoteStoreAdapter.StoreViewHolder>() {

    private val list = ArrayList<NoteStore>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<NoteStore>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class StoreViewHolder(private val binding: NoteListBinding) : ViewHolder(binding.root) {
        fun onBind(pos: Int) {
            binding.titleNoteList.text = list[pos].title
            binding.descriptionNoteList.text = list[pos].description
            binding.dateNoteList.text = list[pos].date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder =
        StoreViewHolder(
            NoteListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.onBind(position)
    }
}