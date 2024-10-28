package com.example.feedback3.actividades

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.feedback3.R
import com.example.feedback3.baseDeDatos.NovelaDatabaseHelper

class AgregarResenaActivity : AppCompatActivity() {
    private lateinit var editTextResena: EditText
    private lateinit var btnGuardarResena: Button
    private lateinit var btnVolver: Button
    private lateinit var novelaDbHelper: NovelaDatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tituloNovela: String

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("UsuarioPreferences", MODE_PRIVATE)
        aplicarTema()
        recreate()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_resena)

        // Inicializar los elementos y obtener el título de la novela
        editTextResena = findViewById(R.id.editTextResena)
        btnGuardarResena = findViewById(R.id.btnGuardarResena)
        btnVolver = findViewById(R.id.btnVolver)
        novelaDbHelper = NovelaDatabaseHelper(this)
        tituloNovela = intent.getStringExtra("tituloNovela") ?: ""

        // Configurar el botón para guardar la reseña
        btnGuardarResena.setOnClickListener {
            val resena = editTextResena.text.toString()
            if (resena.isNotEmpty()) {
                val exito = novelaDbHelper.agregarResena(tituloNovela, resena)
                if (exito) {
                    Toast.makeText(this, "Reseña agregada", Toast.LENGTH_SHORT).show()
                    finish() // Cierra la actividad después de agregar la reseña
                } else {
                    Toast.makeText(this, "Error al agregar reseña", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Escribe una reseña", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el botón para volver a la pantalla anterior
        btnVolver.setOnClickListener {
            finish()  // Simplemente termina la actividad para volver a MainActivity
        }
    }

    private fun aplicarTema() {
        val temaOscuro = sharedPreferences.getBoolean("temaOscuro", false)
        setTheme(if (temaOscuro) R.style.Theme_Feedback3_Night else R.style.Theme_Feedback3_Day)
    }
}
