package com.example.myapplication.userInterface.fragment.fireStore

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentFireStoreBinding
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreFragment :
    BaseFragment<FragmentFireStoreBinding>(FragmentFireStoreBinding::inflate) {

    private lateinit var firestore: FirebaseFirestore
    private val adapter: NoteStoreAdapter by lazy {
        NoteStoreAdapter()
    }


    override fun setUpUI() {
        firestore = FirebaseFirestore.getInstance()
        binding.buttonAdd.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", "firestore")
            findNavController().navigate(R.id.noteFragment, bundle)
        }

        firestore.collection("model").get().addOnSuccessListener { task ->
            val taskList: List<NoteStore> = task.toObjects(NoteStore::class.java)
            adapter.setList(taskList)
        }.addOnFailureListener { exception ->
            Log.w("ololo", "ERROR", exception)
        }
        binding.recyclerView.adapter = adapter
    }

}