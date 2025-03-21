package com.cocido.taily_app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cocido.taily_app.MainActivity
import com.cocido.taily_app.R
import com.cocido.taily_app.data.api.RetrofitInstance
import com.cocido.taily_app.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    // Launcher para manejar el resultado del login con Google
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken

            if (idToken != null) {
                loginWithGoogleToken(idToken)
            } else {
                Toast.makeText(this, "No se pudo obtener el token", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            Toast.makeText(this, "Fallo autenticación con Google", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Login
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Referencias a los elementos
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        val btnGoogle = findViewById<FrameLayout>(R.id.btnGoogle)
        val btnApple = findViewById<FrameLayout>(R.id.btnApple)
        val btnTikTok = findViewById<FrameLayout>(R.id.btnTiktok)

        // Inicializar Google Sign-In
        googleSignInClient = GoogleSignIn.getClient(this, getGoogleSignInOptions())

        // Observar cambios en la respuesta del login
        loginViewModel.loginResponse.observe(this) { response ->
            response?.let {
                // Guardar token
                getSharedPreferences("auth", MODE_PRIVATE)
                    .edit()
                    .putString("token", it.token)
                    .apply()

                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()

                // Ir a la pantalla principal
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        loginViewModel.errorMessage.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        // Acción al presionar "Iniciar Sesión"
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.login(email, password)
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Ir a la pantalla de registro
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Acción "Olvidé mi contraseña"
        tvForgotPassword.setOnClickListener {
            // TODO: Implementar recuperación de contraseña
        }

        // Acciones para los botones de redes sociales
        btnGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }

        btnApple.setOnClickListener {
            // TODO: Implementar autenticación con Apple
            Toast.makeText(this, "Login con Apple", Toast.LENGTH_SHORT).show()
        }

        btnTikTok.setOnClickListener {
            // TODO: Implementar login con TikTok
            Toast.makeText(this, "Login con TikTok", Toast.LENGTH_SHORT).show()
        }
    }

    // Configuración de Google Sign-In
    private fun getGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    // Enviar el token de Google a la API de tu backend
    private fun loginWithGoogleToken(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.loginWithGoogle(token)
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "Login con Google exitoso", Toast.LENGTH_SHORT).show()
                        // TODO: Guardar token de sesión de tu API si es necesario
                    } else {
                        Toast.makeText(this@LoginActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Error de red", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
