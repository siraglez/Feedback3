package com.example.feedback3.actividades

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.feedback3.R
import com.example.feedback3.baseDeDatos.NovelaDatabaseHelper
import com.example.feedback3.adaptadores.NovelaAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var novelaDbHelper: NovelaDatabaseHelper
    private lateinit var listViewNovelas: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        novelaDbHelper = NovelaDatabaseHelper(this)
        listViewNovelas = findViewById(R.id.listViewNovelas)
        mostrarNovelas()

        findViewById<Button>(R.id.btnAgregarNovela).setOnClickListener {
            // Redirigir a AgregarNovelaActivity
            val intent = Intent(this, AgregarNovelaActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.btnConfiguracion).setOnClickListener {
            // Redirigir a ConfiguracionActivity
            val intent = Intent(this, ConfiguracionActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.btnLogout).setOnClickListener {
            // Cerrar sesión y volver a LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finalizar la actividad actual para que no se pueda volver
        }
    }

    private fun mostrarNovelas() {
        val novelas = novelaDbHelper.obtenerNovelas()
        if (novelas.isNotEmpty()) {
            val adapter = NovelaAdapter(this, novelas)
            listViewNovelas.adapter = adapter
        } else {
            Toast.makeText(this, "No hay novelas disponibles", Toast.LENGTH_SHORT).show()
        }
    }
}
