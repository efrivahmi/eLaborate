package com.efrivahmi.elaborate.ui.login.forget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.efrivahmi.elaborate.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var viewModel: ForgetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")
        binding.emailUser.text = email

        // Tambahkan listener pada tombol "Forgot Password"
        binding.verifyButton.setOnClickListener {
            val email = binding.emailUser.text.toString().trim()
            viewModel.forgotPassword(email)
        }
    }

    private fun showToast(message: String) {
        // Tampilkan pesan toast kepada pengguna
    }
}