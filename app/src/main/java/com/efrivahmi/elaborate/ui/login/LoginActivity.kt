package com.efrivahmi.elaborate.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.efrivahmi.elaborate.data.model.UserLogin
import com.efrivahmi.elaborate.databinding.ActivityLoginBinding
import com.efrivahmi.elaborate.ui.main.MainActivity
import com.efrivahmi.elaborate.ui.register.RegisterActivity
import com.efrivahmi.elaborate.ui.login.forget.ForgetPasswordActivity
import com.efrivahmi.elaborate.ui.welcome.WelcomeActivity
import com.efrivahmi.elaborate.utils.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels { factory }
    private var incorrectPasswordCount: Int = 0
    private var isLoginAllowed: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickButton()

        factory = ViewModelFactory.getInstance(this)
    }

    private fun clickButton() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText2.text.toString().trim()
            val password = binding.passwordEditText2.text.toString().trim()

            if (!isLoginAllowed) {
                Toast.makeText(this, "Login is not allowed. Please use the Forgot Password feature.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.isEmpty() && password.isEmpty() && !isValidEmail(email)) {
                binding.emailEditText2.error = FILL_EMAIL
                binding.passwordEditText2.error = FILL_PASSWORD
            } else {
                showLoading()
                uploadData(email, password)
                loginViewModel.login()
                showToast("Invalid Email or Password")
            }
        }
        binding.arrow.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
        binding.loginAction.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.tvForgetPasswordAction.setOnClickListener {
            val email = binding.emailEditText2.text.toString().trim()

            if (email.isNotEmpty()) {
                handleIncorrectPassword(email)
            }
        }
    }

    private fun showLoading() {
        loginViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun uploadData(email: String, password: String) {
        if (password.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            return
        }

        loginViewModel.uploadLoginData(email, password)
        loginViewModel.login.observe(this) { response ->
            if (!response.error) {
                showToast(response.message)
                saveSession(
                    UserLogin(
                        email = response.email,
                        password = password
                    )
                )
                navigateToMain()
            } else {
                showToast(response.message)
                if (response.message == "Invalid password") {
                    incorrectPasswordCount++
                    handleIncorrectPassword(email)
                    if (incorrectPasswordCount >= 1) {
                        Toast.makeText(this, "Please use the forgot password feature", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            this, message, Toast.LENGTH_SHORT
        ).show()
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun saveSession(session: UserLogin) {
        loginViewModel.saveSession(session)
    }

    private fun handleIncorrectPassword(email: String) {
        incorrectPasswordCount++

        if (incorrectPasswordCount >= 1) {
            isLoginAllowed = false

            val intent = Intent(this, ForgetPasswordActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        private const val FILL_PASSWORD = "Please fill in the password field"
        private const val FILL_EMAIL = "Please fill in the email field"
    }
}