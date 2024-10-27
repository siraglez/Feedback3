package com.example.feedback3.dataClasses

data class Usuario(
    val email: String,
    val password: String,
    var temaOscuro: Boolean = false
)
