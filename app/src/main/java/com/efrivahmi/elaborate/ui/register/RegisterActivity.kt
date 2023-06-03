package com.efrivahmi.elaborate.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.efrivahmi.elaborate.R
import com.efrivahmi.elaborate.ui.custom.Password

class RegisterActivity : AppCompatActivity() {

    private lateinit var passwordEditText: Password
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        passwordEditText = findViewById(R.id.cpasswordEditText)
        confirmPasswordEditText = findViewById(R.id.passwordEditText)
        registerButton = findViewById(R.id.registerButton)

        passwordEditText.setConfirmPasswordEditText(confirmPasswordEditText)

        registerButton.setOnClickListener {
            if (passwordEditText.isPasswordValid()) {
                // Password valid
            } else {
                // Password tidak valid
            }
        }
    }
}
