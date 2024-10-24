package com.example.feedback3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AgregarNovelaActivity : AppCompatActivity() {

    private lateinit var etTitulo: EditText
    private lateinit var etAutor: EditText
    private lateinit var etAnioPublicacion: EditText
    private lateinit var etSinopsis: EditText
    private lateinit var etResenas: EditText
    private lateinit var btnSave: Button
    private lateinit var dbHelper: NovelaDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_novela)

        //Inicializar la base de datos
        dbHelper = NovelaDatabaseHelper(this)

        //Inicializar las vistas
        etTitulo = findViewById(R.id.etTitulo)
        etAutor = findViewById(R.id.etAutor)
        etAnioPublicacion = findViewById(R.id.etAnioPublicacion)
        etSinopsis = findViewById(R.id.etSinopsis)
        etResenas = findViewById(R.id.etResenas)
        btnSave = findViewById(R.id.btnSave)

        //Botón para guardar novela
        btnSave.setOnClickListener { saveNovel() }
    }

    private fun saveNovel() {
        val titulo = etTitulo.text.toString()
        val autor = etAutor.text.toString()
        val anioPublicacion = etAnioPublicacion.text.toString().toIntOrNull() ?: 0
        val sinopsis = etSinopsis.text.toString()
        val resenas = etResenas.text.toString()

        // Validar los datos antes de agregar la novela
        if (titulo.isEmpty() || autor.isEmpty() || sinopsis.isEmpty()) {
            Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear el objeto Novela
        val novela = Novela(titulo = titulo, autor = autor, anioPublicacion = anioPublicacion, sinopsis = sinopsis, resenas = listOf(resenas))

        // Insertar la novela en la base de datos SQLite
        val success = dbHelper.addNovela(novela)

        if (success) {
            Toast.makeText(this, "Novela guardada exitosamente", Toast.LENGTH_SHORT).show()
            finish() // Volver a la actividad anterior después de guardar
        } else {
            Toast.makeText(this, "Error al guardar la novela", Toast.LENGTH_SHORT).show()
        }
    }
}
