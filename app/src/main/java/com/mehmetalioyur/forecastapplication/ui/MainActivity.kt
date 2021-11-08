package com.mehmetalioyur.forecastapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mehmetalioyur.forecastapplication.R
import com.mehmetalioyur.forecastapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

       // val navHostFragment =supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
       // binding.bottomNavigationMenu.setupWithNavController(navHostFragment.findNavController())

    }
}