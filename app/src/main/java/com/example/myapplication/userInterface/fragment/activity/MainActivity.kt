package com.example.myapplication.userInterface.fragment.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.userInterface.sharedPreferences.app.App

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: NavController
    private var listView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_activity_fragment) as NavHostFragment
        controller = navHostFragment.navController

        if (App.prefs.isBoardShow()) {
            controller.navigate(R.id.addNoteFragment)
        } else {
            App.prefs.saveBoardState()
            controller.navigate(R.id.onBoardFragment)
        }

        controller.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.onBoardFragment || destination.id == R.id.noteFragment || destination.id == R.id.phoneFragment) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }

        initBottomView()
        listView = findViewById(R.id.listView)
    }

    private fun initBottomView() {
        binding.bottomNavigation.apply {
            setupWithNavController(controller)
            itemIconTintList = null
        }
    }
}