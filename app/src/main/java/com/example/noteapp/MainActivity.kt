package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var floatActionBtn: FloatingActionButton
    lateinit var sort_spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        floatActionBtn = findViewById(R.id.floatingActionButton)
        bottomNavigationView = findViewById(R.id.bottomNav)
        sort_spinner = findViewById(R.id.sort_spinner)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                 R.id.noteFragment2 -> {
                     bottomNavigationView.visibility = View.GONE
                     floatActionBtn.visibility = View.GONE
                     sort_spinner.visibility = View.GONE
                     supportActionBar?.hide()
                 }
                R.id.homeFragment, R.id.favoriteFragment -> {
                    sort_spinner.visibility = View.VISIBLE
                    bottomNavigationView.visibility = View.VISIBLE
                    floatActionBtn.visibility = View.VISIBLE
                    supportActionBar?.show()
                }
            }
        }

        floatActionBtn.setOnClickListener {
            navController.navigate(R.id.noteFragment2)
        }

        ArrayAdapter.createFromResource(
                this,
                R.array.sort_array,
                android.R.layout.simple_spinner_item
        ).also {adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sort_spinner.adapter = adapter
        }
    }



}