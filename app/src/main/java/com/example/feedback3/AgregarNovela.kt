package com.example.feedback3

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AgregarNovela(onAgregarNovela: (Novela) -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var sinopsis by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") })
        TextField(value = autor, onValueChange = { autor = it }, label = { Text("Autor") })
        TextField(value = anio, onValueChange = { anio = it }, label = { Text("Año de Publicación") })
        TextField(value = sinopsis, onValueChange = { sinopsis = it }, label = { Text("Sinopsis") })

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (titulo.isNotEmpty() && autor.isNotEmpty() && anio.isNotEmpty() && sinopsis.isNotEmpty()) {
                onAgregarNovela(Novela(titulo, autor, anio.toInt(), sinopsis))
            }
        }) {
            Text("Agregar Novela")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAgregarNovela() {
    AgregarNovela(onAgregarNovela = {})
}
