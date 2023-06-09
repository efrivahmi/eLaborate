package com.efrivahmi.elaborate.ui.login.forget

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.efrivahmi.elaborate.R
import com.efrivahmi.elaborate.data.response.FpResponse
import com.efrivahmi.elaborate.data.response.VerifyCode
import com.efrivahmi.elaborate.databinding.ActivityForgetPasswordBinding
import com.efrivahmi.elaborate.ui.login.LoginActivity
import com.efrivahmi.elaborate.ui.login.editpassword.EditPasswordActivity
import com.efrivahmi.elaborate.utils.HelperToast
import com.efrivahmi.elaborate.utils.ViewModelFactory

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: ForgetPasswordViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        val email = intent.getStringExtra("email")
        email?.let {
            binding.emailUser.text = it
        }

        binding.generatedVerifyButton.setOnClickListener {
            val email = binding.emailUser.text.toString().trim()
            if (email.isNotEmpty()) {
                viewModel.forgotPassword(email)
            } else {
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
            }
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

        val verificationCodeEditTexts = listOf(
            binding.box1,
            binding.box2,
            binding.box3,
            binding.box4,
            binding.box5,
            binding.box6
        )

        for (editText in verificationCodeEditTexts) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    var verificationCode = ""
                    for (editText in verificationCodeEditTexts) {
                        verificationCode += editText.text.toString()
                    }
                    binding.verifyButton.isEnabled = verificationCode.length == 6
                }
            })
        }

        binding.verifyButton.setOnClickListener {
            val verificationCode = getVerificationCodeFromEditTexts(verificationCodeEditTexts)
            val email = binding.emailUser.text.toString().trim()
            if (email.isNotEmpty()) {
                viewModel.sendVerificationCode(email, verificationCode)
            } else {
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
            }
        }
        binding.arrow.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar3.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showToastMessage(toast: HelperToast<String>) {
        val message = toast.getContentIfNotHandled()
        message?.let {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    @Suppress("DEPRECATION")
    private val itemForgetHandler = Handler()

    @SuppressLint("SetTextI18n")
    private fun handleVerifyCodeResponse(verifyCode: VerifyCode) {
        if (verifyCode.error) {
            Toast.makeText(this, "Verification code verified", Toast.LENGTH_SHORT).show()
        } else {
            showLoading()
            binding.itemForget.visibility = View.VISIBLE
            binding.error.text = "Successful verification, please change the password"
            val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
            binding.itemForget.startAnimation(slideUpAnimation)
            itemForgetHandler.postDelayed({
                startActivity(Intent(this, EditPasswordActivity::class.java))
                finish()
            }, 4000)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleForgotPasswordResponse(fpResponse: FpResponse) {
        if (fpResponse.error) {
            Toast.makeText(this, "Forgot password request sent", Toast.LENGTH_SHORT).show()
            binding.generatedVerifyButton.isEnabled = false
            binding.generatedVerifyButton.text = "Request Sent"
        } else {
            Toast.makeText(
                this,
                "Code has been send on your email",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getVerificationCodeFromEditTexts(editTexts: List<EditText>): String {
        var verificationCode = ""
        for (editText in editTexts) {
            verificationCode += editText.text.toString()
        }
        return verificationCode
    }
}