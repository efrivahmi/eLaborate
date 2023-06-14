package com.efrivahmi.elaborate.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.efrivahmi.elaborate.R
import com.efrivahmi.elaborate.databinding.ActivityMainBinding
import com.efrivahmi.elaborate.ui.main.home.HomeFragment
import com.efrivahmi.elaborate.ui.main.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isItem1Selected = false
    private var isItem2Selected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    if (!isItem1Selected) {
                        isItem1Selected = true
                        isItem2Selected = false
                        replaceFragment(HomeFragment())
                    }
                }
            }

            true
        }

        binding.bottomNavigationView2.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profile -> {
                    if (!isItem2Selected) {
                        isItem1Selected = false
                        isItem2Selected = true
                        replaceFragment(ProfileFragment())
                    }
                }
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}