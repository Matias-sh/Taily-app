package com.example.taily

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var notificationsButton: ImageView
    private lateinit var menuButton: ImageView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        notificationsButton = findViewById(R.id.btn_notifications)
        menuButton = findViewById(R.id.btn_menu)
        bottomNavigation = findViewById(R.id.bottom_navigation)

        // Manejar clics en botones
        notificationsButton.setOnClickListener {
            // Abrir notificaciones
        }

        menuButton.setOnClickListener {
            // Abrir menú
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_scan -> {
                    // Abrir escaneo QR
                    true
                }
                R.id.nav_health -> {
                    // Abrir sección de salud
                    true
                }
                R.id.nav_home -> {
                    // Volver al inicio
                    true
                }
                R.id.nav_store -> {
                    // Abrir tienda
                    true
                }
                R.id.nav_breeding -> {
                    // Abrir sección de cruza
                    true
                }
                else -> false
            }
        }
    }
}
