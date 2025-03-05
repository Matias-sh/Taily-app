package com.example.tailyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tailyapp.ui.theme.TailyappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TailyappTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar()
                    }) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MenuWidget() // Usamos la misma UI
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHelloWorld() {
    TailyappTheme {
        MenuWidget() // Usamos la misma UI
    }
}

@Composable
fun MenuWidget() {
    // Aquí iría el contenido principal de tu aplicación
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Contenido principal de TailyApp")
    }
}

@Composable
fun BottomNavigationBar() {
    var selectedItem by remember { mutableStateOf(0) }

    val navigationItems = listOf(
        NavigationItem("Inicio", Icons.Filled.Home),
        NavigationItem("Buscar", Icons.Filled.Search),
        NavigationItem("Favoritos", Icons.Filled.Favorite),
        NavigationItem("Perfil", Icons.Filled.Person),
        NavigationItem("Ajustes", Icons.Filled.Settings)
    )

    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}

// Clase de datos para los elementos de navegación
data class NavigationItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
