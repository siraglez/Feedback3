package com.example.feedback3.actividades

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.example.feedback3.R
import com.example.feedback3.baseDeDatos.NovelaDatabaseHelper
import com.example.feedback3.adaptadores.NovelaAdapter
import com.example.feedback3.dataClasses.Novela

class MainActivity : AppCompatActivity() {
    private lateinit var novelaDbHelper: NovelaDatabaseHelper
    private lateinit var adapter: NovelaAdapter
    private lateinit var listViewNovelas: ListView
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val REQUEST_CODE_DETALLES = 1
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("UsuarioPreferences", MODE_PRIVATE)
        aplicarTema()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        novelaDbHelper = NovelaDatabaseHelper(this)
        listViewNovelas = findViewById(R.id.listViewNovelas)

        adapter = NovelaAdapter(this, mutableListOf())
        listViewNovelas.adapter = adapter

        val btnConfiguracion = findViewById<ImageButton>(R.id.btnConfiguracion)
        val btnLogout = findViewById<ImageButton>(R.id.btnLogout)

        // Configura el color de los iconos según el tema oscuro
        val isNightMode = sharedPreferences.getBoolean("modoNoche", false)
        val iconColor = if (isNightMode) {
            ContextCompat.getColor(this, R.color.textColorNight)
        } else {
            ContextCompat.getColor(this, R.color.textColor)
        }
        ImageViewCompat.setImageTintList(btnConfiguracion, ContextCompat.getColorStateList(this, iconColor))
        ImageViewCompat.setImageTintList(btnLogout, ContextCompat.getColorStateList(this, iconColor))

        // Configura el clic en el elemento de la lista para ver detalles
        listViewNovelas.setOnItemClickListener { _, _, position, _ ->
            val novela = adapter.getItem(position) as Novela
            val intent = Intent(this, DetallesNovelaActivity::class.java).apply {
                putExtra("titulo", novela.titulo)
                putExtra("autor", novela.autor)
                putExtra("anio", novela.anioPublicacion)
                putExtra("sinopsis", novela.sinopsis)
                putExtra("esFavorita", novela.esFavorita)
            }
            startActivityForResult(intent, REQUEST_CODE_DETALLES)
        }

        mostrarNovelas()

        //Botón para agregar novelas
        findViewById<Button>(R.id.btnAgregarNovela).setOnClickListener {
            val intent = Intent(this, AgregarNovelaActivity::class.java)
            startActivity(intent)
        }

        //Botón para ir a la pantalla de configuración
        btnConfiguracion.setOnClickListener {
            val intent = Intent(this, ConfiguracionActivity::class.java)
            startActivity(intent)
        }

        //Botón para cerrar sesión
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun mostrarNovelas() {
        val novelas = novelaDbHelper.obtenerNovelas()
        if (novelas.isNotEmpty()) {
            adapter.clear()
            adapter.addAll(novelas)
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this, "No hay novelas disponibles", Toast.LENGTH_SHORT).show()
        }
    }

    // Actualizar la lista de novelas si cambia el estado de favorito en DetallesNovelaActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_DETALLES && resultCode == RESULT_OK) {
            val favoritoActualizado = data?.getBooleanExtra("favorito_actualizado", false) ?: false
            if (favoritoActualizado) {
                mostrarNovelas()  // Recarga la lista de novelas
            }
        }
    }

    private fun aplicarTema() {
        val temaOscuro = sharedPreferences.getBoolean("modoNoche", false)
        setTheme(if (temaOscuro) R.style.Theme_Feedback3_Night else R.style.Theme_Feedback3_Day)
    }

    override fun onResume() {
        super.onResume()
        mostrarNovelas()
    }
}
