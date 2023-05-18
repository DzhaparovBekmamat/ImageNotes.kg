package com.example.myapplication.userInterface.fragment.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.userInterface.fragment.addNote.AddNoteFragment
import com.example.myapplication.userInterface.fragment.bottomNavigation.ContactsFragment
import com.example.myapplication.userInterface.fragment.bottomNavigation.ProfileFragment
import com.example.myapplication.userInterface.sharedPreferences.app.Application

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val addNoteFragment = AddNoteFragment()
        val contactsFragment = ContactsFragment()
        val profileFragment = ProfileFragment()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_activity_fragment) as NavHostFragment
        controller = navHostFragment.navController
        if (Application.prefs.isBoardShow()) {
            controller.navigate(R.id.addNoteFragment)
        } else {
            Application.prefs.saveBoardState()
            controller.navigate(R.id.onBoardFragment)
        }

        controller.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.onBoardFragment || destination.id == R.id.noteFragment) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.addNoteFragment -> replaceFragment(addNoteFragment)
                R.id.contactsFragment -> replaceFragment(contactsFragment)
                R.id.profileFragment -> replaceFragment(profileFragment)
            }
            true
        }
    }

    @SuppressLint("CommitTransaction")
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_activity_fragment, fragment)
            .addToBackStack(null).commit()
    }
}