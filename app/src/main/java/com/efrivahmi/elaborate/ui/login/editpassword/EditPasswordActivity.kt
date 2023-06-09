package com.efrivahmi.elaborate.ui.login.editpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.efrivahmi.elaborate.R
import com.efrivahmi.elaborate.databinding.ActivityEditPasswordBinding

class EditPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}