package com.example.myapplication.userInterface.fragment.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.NoteContactsBinding

/**
 * Author: Dzhaparov Bekmamat
 */
class ContactsAdapter(
    private val call: (number: String) -> Unit,
    private val chat: (number: String) -> Unit
) :
    ListAdapter<ContactsModel, ContactsAdapter.ContactViewHolder>(ContactDiffUtil()) {
    class ContactDiffUtil : DiffUtil.ItemCallback<ContactsModel>() {
        override fun areItemsTheSame(oldItem: ContactsModel, newItem: ContactsModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ContactsModel, newItem: ContactsModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class ContactViewHolder(private val binding: NoteContactsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: ContactsModel) {
            binding.nameContactsFragment.text = model.name
            binding.phoneContactsFragment.text = model.phoneNumber
            binding.btnCall.setOnClickListener {
                model.phoneNumber?.let { it1 -> call(it1) }
            }
            binding.btnChat.setOnClickListener {
                model.phoneNumber?.let { it1 -> chat(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContactViewHolder(
        NoteContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val model = getItem(position)
        holder.onBind(model)
    }
}
