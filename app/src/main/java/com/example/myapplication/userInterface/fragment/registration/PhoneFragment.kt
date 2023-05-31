@file:Suppress("DEPRECATION")

package com.example.myapplication.userInterface.fragment.registration

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentPhoneBinding
import com.example.myapplication.userInterface.fragment.activity.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class PhoneFragment : BaseFragment<FragmentPhoneBinding>(FragmentPhoneBinding::inflate) {
    private lateinit var callback: OnVerificationStateChangedCallbacks
    private lateinit var mAuth: FirebaseAuth
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor

    companion object {
        private const val SELECT_PHOTO_REQUEST = 1
    }

    @SuppressLint("CommitPrefEdits")
    override fun setUpUI() {
        mAuth = FirebaseAuth.getInstance()
        binding.btnCheck.setOnClickListener {
            val phone = binding.edPhone.text.toString().trim()
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone, 60, TimeUnit.SECONDS, activity as MainActivity, callback
            )
            saveData()
        }
        binding.btnGoogle.setOnClickListener {
            signIn()
        }
        binding.addPhotoRegistration.setOnClickListener {
            openGalleryForImage()
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        editor = sharedPreferences.edit()
        loadData()
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, SELECT_PHOTO_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHOTO_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri = data.data ?: return
            saveImageUriToSharedPreferences(selectedImageUri)
        }
    }

    private fun saveImageUriToSharedPreferences(selectedImageUri: Uri) {
        editor.putString("profileImageUri", selectedImageUri.toString())
        editor.apply()
    }

    private fun loadData() {
        val name = sharedPreferences.getString("name", "")
        val lastName = sharedPreferences.getString("lastName", "")
        val phoneNumber = sharedPreferences.getString("phoneNumber", "")
        val maritalStatus = sharedPreferences.getBoolean("maritalStatus", false)
        binding.edName.setText(name)
        binding.edSurname.setText(lastName)
        binding.edPhone.setText(phoneNumber)
        binding.switchProfile.isChecked = maritalStatus
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveData() {
        val editor = sharedPreferences.edit()
        editor.putString("firstName", binding.edName.text.toString())
        editor.putString("lastName", binding.edSurname.text.toString())
        editor.putString("phoneNumber", binding.edPhone.text.toString())
        editor.putBoolean("maritalStatus", binding.switchProfile.isChecked)
        editor.apply()
    }

    override fun setUpObserver() {
        initPhone()
        initGoogle()
    }

    private fun initGoogle() {
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    account.idToken?.let { result ->
                        firebaseAuthWithGoogle(result)
                    }
                }
            } catch (e: ApiException) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }

    private fun initPhone() {
        callback = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.addNoteFragment)
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.e("ololo", p0.message.toString())
            }

            override fun onCodeSent(id: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(id, p1)
                val bundle = Bundle()
                bundle.putString("id", id)
                findNavController().navigate(R.id.codeFragment, bundle)
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun initGoogleClient(): GoogleSignInClient {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        return GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
    }

    private fun signIn() {
        val result = initGoogleClient()
        launcher.launch(result.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            }
        }
    }
}