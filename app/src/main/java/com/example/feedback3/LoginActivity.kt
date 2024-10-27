package com.example.feedback3

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var dbHelper: UsuarioDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.aplicarTema(this)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        dbHelper = UsuarioDatabaseHelper(this)

        btnLogin.setOnClickListener { login() }
        btnRegister.setOnClickListener { register() }
    }

    private fun login() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Lógica para iniciar sesión
        val user = dbHelper.getUser(email) // Debe devolver el usuario por correo

        if (user != null && user.password == password) { // Asegúrate de que la contraseña coincida
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
            // Navegar a la pantalla principal
            startActivity(Intent(this, PantallaPrincipalActivity::class.java))
        } else {
            Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun register() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (validateInputs(email, password)) {
            // Verificar si el usuario ya existe
            if (dbHelper.getUser(email) != null) {
                Toast.makeText(this, "El correo electrónico ya está en uso", Toast.LENGTH_SHORT).show()
                return
            }

            // Registrar nuevo usuario en la base de datos
            if (dbHelper.addUser(Usuario(email, password))) {
                Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        // Validar correo electrónico
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor ingresa un correo electrónico válido", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validar contraseña
        if (password.isEmpty() || password.length < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
