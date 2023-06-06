package com.efrivahmi.elaborate.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.efrivahmi.elaborate.R
import com.efrivahmi.elaborate.databinding.ActivityLoginBinding
import com.efrivahmi.elaborate.utils.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
    }
}