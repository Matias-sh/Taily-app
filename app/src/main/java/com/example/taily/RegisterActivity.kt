package com.example.taily

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nameEditText: EditText = findViewById(R.id.et_name)
        val emailEditText: EditText = findViewById(R.id.et_email)
        val passwordEditText: EditText = findViewById(R.id.et_password)
        val confirmPasswordEditText: EditText = findViewById(R.id.et_confirm_password)
        val registerButton: Button = findViewById(R.id.btn_register)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword == password) {
                Toast.makeText(this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show()
                finish() // Volver a la pantalla de inicio de sesión
            } else {
                Toast.makeText(this, "Completa todos los campos o revisa las contraseñas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
