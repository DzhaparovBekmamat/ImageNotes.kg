package com.example.myapplication.userInterface.fragment.note

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.model.NoteModel
import com.example.myapplication.databinding.FragmentNoteBinding
import com.example.myapplication.userInterface.fragment.noteAdapter.NoteAdapter
import com.example.myapplication.userInterface.sharedPreferences.app.App
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NoteFragment : BaseFragment<FragmentNoteBinding>(FragmentNoteBinding::inflate),
    NoteAdapter.IOnItem {
    private lateinit var firestore: FirebaseFirestore
    private val model: MutableMap<String, Any> = HashMap()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setUpUI() {
        firestore = FirebaseFirestore.getInstance()
        if (arguments != null) {
            binding.buttonNoteFragment.setOnClickListener {
                val titleString = binding.titleNoteFragment.text.toString()
                val descriptionString = binding.descriptionNoteFragment.text.toString()
                val formatter = DateTimeFormatter.ofPattern("HH:mm | dd-MM-yyyy")
                val current = LocalDateTime.now().format(formatter)
                saveFirestore(titleString, descriptionString, current)
            }
        } else {
            binding.buttonNoteFragment.setOnClickListener {
                saveRoom()
            }
        }
    }

    private fun saveFirestore(title: String, desc: String, date: String) {
        model["title"] = title
        model["description"] = desc
        model["date"] = date
        firestore.collection("model").add(model).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "Удачно добавлен в FireStore", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "${it.exception?.message}", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigateUp()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setUpObserver() {
        binding.buttonFragmentNote.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveRoom() {
        val titleString = binding.titleNoteFragment.text.toString()
        val descriptionString = binding.descriptionNoteFragment.text.toString()
        val formatter = DateTimeFormatter.ofPattern("hh:mm | dd-mm-yyyy")
        val current = LocalDateTime.now().format(formatter)

        if (titleString.isEmpty() || descriptionString.isEmpty()) {
            findNavController().navigateUp()
        } else {
            App.database.getDao().setNote(
                NoteModel(
                    title = titleString, description = descriptionString, date = current
                )
            )
            findNavController().navigateUp()
        }
    }

    override fun delete(position: Int) {
    }

    override fun share(position: Int) {
    }
}