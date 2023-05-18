package com.example.myapplication.userInterface.fragment.board

import android.annotation.SuppressLint
import android.content.Intent
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentOnBoardBinding
import com.example.myapplication.userInterface.sharedPreferences.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class OnBoardFragment : BaseFragment<FragmentOnBoardBinding>(FragmentOnBoardBinding::inflate),
    BoardAdapter.OpenListener {

    private lateinit var adapter: BoardAdapter
    private lateinit var authentication: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var launcher: ActivityResultLauncher<Intent>
    override fun setUpUI() {
        authentication = Firebase.auth
        adapter = BoardAdapter(this)
        binding.viewPager.adapter = adapter
        binding.wormDotsIndicator.setViewPager2(binding.viewPager)


        binding.skipButton.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            binding.viewPager.setCurrentItem(currentItem + 2, true)
        }
        binding.nextButton.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            binding.viewPager.setCurrentItem(currentItem + 1, true)
        }
        initFirebase()
        initGoogleClient()
    }

    private fun initFirebase() {
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
                Log.e("ololoerror", e.message.toString())
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun initGoogleClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun signIn() {
        val result = initGoogleClient()
        launcher.launch(result.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        authentication.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun open() {
        signIn()
    }
}
