@file:Suppress("DEPRECATION")

package com.example.myapplication.userInterface.fragment.profile

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.net.Uri
import android.preference.PreferenceManager
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentProfileBinding

@Suppress("DEPRECATION")
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private lateinit var sharedPreferences: SharedPreferences
    override fun setUpUI() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        binding.logOut.setOnClickListener {
            deleteData()
        }
        loadData()
        loadPhotoFromSharedPreferences()
    }

    private fun loadPhotoFromSharedPreferences() {
        val imageUriString = sharedPreferences.getString("profileImageUri", "")
        if (imageUriString != null && imageUriString.isNotEmpty()) {
            val imageUri = Uri.parse(imageUriString)
            binding.imageViewProfile.setImageURI(imageUri)
        }
    }

    private fun loadData() {
        val firstName = sharedPreferences.getString("firstName", "")
        val lastName = sharedPreferences.getString("lastName", "")
        val phoneNumber = sharedPreferences.getString("phoneNumber", "")
        val maritalStatus = sharedPreferences.getBoolean("maritalStatus", false)
        binding.nameProfile.text = firstName
        binding.surnameProfile.text = lastName
        binding.phoneNumberProfile.text = phoneNumber
        binding.switchProfile.isChecked = maritalStatus
    }

    @SuppressLint("CommitPrefEdits")
    private fun deleteData() {
        val editor = sharedPreferences.edit()
        editor.remove("firstName")
        editor.remove("lastName")
        editor.remove("phoneNumber")
        editor.remove("maritalStatus")
        editor.remove("profileImageUri")
        editor.apply()
        binding.nameProfile.text = ""
        binding.surnameProfile.text = ""
        binding.phoneNumberProfile.text = ""
        binding.switchProfile.isChecked = false
        binding.imageViewProfile.setImageURI(null)
    }
}