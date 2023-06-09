package com.efrivahmi.elaborate.ui.login.forget

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.efrivahmi.elaborate.data.response.FpResponse
import com.efrivahmi.elaborate.data.response.VerifyCode
import com.efrivahmi.elaborate.databinding.ActivityForgetPasswordBinding
import com.efrivahmi.elaborate.ui.login.editpassword.EditPasswordActivity
import com.efrivahmi.elaborate.utils.EmailUtil
import com.efrivahmi.elaborate.utils.HelperToast
import com.efrivahmi.elaborate.utils.ViewModelFactory
import java.util.*

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: ForgetPasswordViewModel by viewModels { factory }

    @Suppress("NAME_SHADOWING")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")
        binding.emailUser.text = email

        binding.generatedVerifyButton.setOnClickListener {
            val email = binding.emailUser.text.toString().trim()
            val verificationCode = generateVerificationCode()
            val username = email.substringBefore('@')
            EmailUtil.sendVerificationCode(email, verificationCode, username)
            viewModel.sendVerificationCode(email, verificationCode)
        }

        viewModel.toastText.observe(this) { toast ->
            toast?.let {
                showToastMessage(toast)
            }
        }

        viewModel.verifyCode.observe(this) { verifyCode ->
            handleVerifyCodeResponse(verifyCode)
        }

        viewModel.forgot.observe(this) { fpResponse ->
            handleForgotPasswordResponse(fpResponse)
        }
    }

    private fun generateVerificationCode(): String {
        return UUID.randomUUID().toString()
    }

    private fun showToastMessage(toast: HelperToast<String>) {
        val message = toast.getContentIfNotHandled()
        message?.let {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleVerifyCodeResponse(verifyCode: VerifyCode) {
        if (verifyCode.error != null) {
            Toast.makeText(this, "Verification code verified", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, EditPasswordActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Failed to verify verification code", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleForgotPasswordResponse(fpResponse: FpResponse) {
        if (fpResponse.error != null) {
            Toast.makeText(this, "Forgot password request sent", Toast.LENGTH_SHORT).show()
            binding.generatedVerifyButton.isEnabled = false
            binding.generatedVerifyButton.text = "Request Sent"
        } else {
            Toast.makeText(this, "Failed to send forgot password request", Toast.LENGTH_SHORT).show()
        }
    }
}