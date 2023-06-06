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
import com.efrivahmi.elaborate.ui.custom.Email
import com.efrivahmi.elaborate.ui.custom.Name
import com.efrivahmi.elaborate.ui.custom.Password
import com.efrivahmi.elaborate.ui.welcome.WelcomeActivity
import com.efrivahmi.elaborate.utils.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameEditText: Name
    private lateinit var emailEditText: Email
    private lateinit var passwordEditText: Password
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
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
        registerButton = findViewById(R.id.registerButton)

        passwordEditText.setConfirmPasswordEditText(confirmPasswordEditText)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (password.isNotEmpty() && password == confirmPassword || !isValidEmail(email)) {
                registerViewModel.uploadRegisData(username, email, password, confirmPassword)
            } else {
                showToast("Password tidak valid")
                showLoading()
                uploadData(username, email, password, confirmPassword)
            }
        }
    }

    private fun uploadData(username: String, email: String, password: String, confirmPassword: String) {
        registerViewModel.uploadRegisData(username, email, password, confirmPassword)
        registerViewModel.regis.observe(this) { response ->
            if (!response.error) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                val errorMessage = response.message
                showToast(errorMessage)
            }
        }
    }

    private fun showLoading() {
        registerViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun showToast(s: String) {
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
}
