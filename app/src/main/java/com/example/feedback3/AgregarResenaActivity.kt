package com.example.feedback3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AgregarResenaActivity : AppCompatActivity() {

    private lateinit var dbHelper: NovelaDatabaseHelper
    private lateinit var tituloNovela: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_resena)

        // Instanciar el helper de base de datos
        dbHelper = NovelaDatabaseHelper(this)

        // Obtener el título de la novela desde el Intent
        tituloNovela = intent.getStringExtra("titulo") ?: ""

        val resenaEditText = findViewById<EditText>(R.id.editTextResena)

        findViewById<Button>(R.id.btnAgregarResena).setOnClickListener {
            val resena = resenaEditText.text.toString()

            if (resena.isNotEmpty() && tituloNovela.isNotBlank()) {
                agregarResena(tituloNovela, resena)
                finish() // Regresar a la pantalla de detalles de la novela
            } else {
                Toast.makeText(this, "Por favor escribe una reseña", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun agregarResena(tituloNovela: String, nuevaResena: String) {
        // Obtener la novela desde la base de datos
        val novela = dbHelper.getAllNovelas().find { it.titulo == tituloNovela }

        if (novela != null) {
            // Crear una nueva lista de reseñas añadiendo la nueva reseña
            val updatedResenas = novela.resenas.toMutableList().apply { add(nuevaResena) }

            // Crear una nueva novela con la lista actualizada de reseñas
            val novelaActualizada = novela.copy(resenas = updatedResenas)

            // Actualizar la novela en la base de datos
            val success = dbHelper.actualizarNovela(novelaActualizada)

            if (success) {
                Toast.makeText(this, "Reseña agregada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al agregar reseña", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Novela no encontrada", Toast.LENGTH_SHORT).show()
        }
    }
}
