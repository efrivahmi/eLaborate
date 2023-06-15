package com.efrivahmi.elaborate.ui.main.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.efrivahmi.elaborate.databinding.ActivityResultBinding
import com.efrivahmi.elaborate.utils.ViewModelFactoryMl

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var factory = ViewModelFactoryMl
    private var resultViewModel: ResultViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactoryMl.getInstance(this)
    }
}