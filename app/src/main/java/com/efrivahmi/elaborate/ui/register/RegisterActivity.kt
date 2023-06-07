package com.efrivahmi.elaborate.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.efrivahmi.elaborate.R
import com.efrivahmi.elaborate.databinding.ActivityRegisterBinding
import com.efrivahmi.elaborate.ui.custom.Password
import com.efrivahmi.elaborate.ui.login.LoginActivity
import com.efrivahmi.elaborate.ui.welcome.WelcomeActivity
import com.efrivahmi.elaborate.utils.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var passwordEditText: Password
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var factory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        passwordEditText = findViewById(R.id.cpasswordEditText)
        confirmPasswordEditText = findViewById(R.id.passwordEditText)
        passwordEditText.setConfirmPasswordEditText(confirmPasswordEditText)

        clickButton()
    }
    private fun clickButton() {
        binding.registerButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.cpasswordEditText.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() && password == confirmPassword || !isValidEmail(email)) {
                binding.usernameEditText.error = FILL_NAME
                binding.emailEditText.error = FILL_EMAIL
                binding.passwordEditText.error = FILL_PASSWORD
            } else {
                showToast()
                uploadData(username, email, password, confirmPassword)
                showLoading()
            }
        }
        binding.arrow.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
        binding.registerAction.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun uploadData(username: String, email: String, password: String, confirmPassword: String) {
        registerViewModel.uploadRegisData(username, email, password, confirmPassword)
        registerViewModel.regis.observe(this) { response ->
            if (!response.error) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun showLoading() {
        registerViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showToast() {
        registerViewModel.toast.observe(this) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    companion object {
        private const val FILL_NAME = "Have to fill your name"
        private const val FILL_PASSWORD = "Have to fill password first"
        private const val FILL_EMAIL = "Have to fill email first"
    }
}
