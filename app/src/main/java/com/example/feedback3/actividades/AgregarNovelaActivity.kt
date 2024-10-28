package com.example.feedback3.actividades

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.feedback3.R
import com.example.feedback3.baseDeDatos.NovelaDatabaseHelper
import com.example.feedback3.dataClasses.Novela

class AgregarNovelaActivity : AppCompatActivity() {
    private lateinit var novelaDbHelper: NovelaDatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_novela)

        sharedPreferences = getSharedPreferences("UsuarioPreferences", MODE_PRIVATE)

        novelaDbHelper = NovelaDatabaseHelper(this)

        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            val titulo = findViewById<EditText>(R.id.etTitulo).text.toString()
            val autor = findViewById<EditText>(R.id.etAutor).text.toString()
            val anioPublicacion = findViewById<EditText>(R.id.etAnio).text.toString()
            val sinopsis = findViewById<EditText>(R.id.etSinopsis).text.toString()

            // Validar que los campos no estén vacíos
            if (titulo.isEmpty() || autor.isEmpty() || anioPublicacion.isEmpty() || sinopsis.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convertir el año de publicación a entero
            val anio: Int
            try {
                anio = anioPublicacion.toInt()

                // Validar que el año sea razonable
                if (anio < 1300 || anio > 2100) {
                    Toast.makeText(this, "Por favor, ingrese un año válido", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "El año de publicación debe ser un número", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear una nueva novela
            val novela = Novela(titulo, autor, anio, sinopsis)

            // Guardar la novela en la base de datos
            novelaDbHelper.agregarNovela(novela)

            // Mostrar mensaje de éxito
            Toast.makeText(this, "Novela agregada con éxito", Toast.LENGTH_SHORT).show()

            // Regresar a la actividad anterior
            finish()
        }
    }

    private fun aplicarTema() {
        val temaOscuro = sharedPreferences.getBoolean("temaOscuro", false)
        setTheme(if (temaOscuro) R.style.Theme_Feedback3_Night else R.style.Theme_Feedback3_Day)
    }

    override fun onResume() {
        super.onResume()
        aplicarTema()
    }
}
