package com.example.feedback3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarResena(novela: Novela, onResenaAgregada: (String) -> Unit) {
    var resena by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Agregar Reseña") }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Reseña para: ${novela.titulo}", style = MaterialTheme.typography.titleMedium)
                TextField(
                    value = resena,
                    onValueChange = { resena = it },
                    label = { Text("Escribe tu reseña") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        if (resena.isNotBlank()) {
                            onResenaAgregada(resena) // Llama la función para agregar reseña
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Agregar Reseña")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AgregarResenaPreview() {
    // Crear un objeto de novela de ejemplo
    val novelaEjemplo = Novela(
        titulo = "Ejemplo de Novela",
        autor = "Autor de Ejemplo",
        anioPublicacion = 2023,
        sinopsis = "Esta es una sinopsis de ejemplo para la novela.",
        resenas = mutableListOf() // Inicializar con una lista vacía
    )

    // Llamar a la función AgregarResena con un lambda vacío para onResenaAgregada
    AgregarResena(novela = novelaEjemplo) { resena ->
    }
}
