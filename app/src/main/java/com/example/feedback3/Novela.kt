package com.example.feedback3

data class Novela(
    val titulo: String,
    val autor: String,
    val anioPublicacion: Int,
    val sinopsis: String,
    var esFavorita: Boolean = false,
    val resenas: MutableList<String> = mutableListOf() // Lista para almacenar reseñas
)

