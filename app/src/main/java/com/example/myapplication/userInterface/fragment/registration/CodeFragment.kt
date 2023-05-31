package com.example.myapplication.userInterface.fragment.registration

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentCodeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class CodeFragment : BaseFragment<FragmentCodeBinding>(FragmentCodeBinding::inflate) {
    private lateinit var mAuth: FirebaseAuth

    override fun setUpUI() {
        mAuth = FirebaseAuth.getInstance()
        if (arguments != null) {
            val id = arguments?.getString("id").toString()
            binding.btnCheck.setOnClickListener {
                val code = binding.edCode.text.toString().trim()
                val credential = PhoneAuthProvider.getCredential(id, code)
                mAuth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        findNavController().navigate(R.id.addNoteFragment)
                    } else {
                        Toast.makeText(
                            requireContext(), "${it.exception?.message}", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}