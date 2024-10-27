package com.example.feedback3

data class Novela(
    val titulo: String,
    val autor: String,
    val anioPublicacion: Int,
    val sinopsis: String,
    val resenas: List<String> = listOf(),
    var esFavorita: Boolean = false
)
