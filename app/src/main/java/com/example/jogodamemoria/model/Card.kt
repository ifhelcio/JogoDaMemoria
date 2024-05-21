package com.example.jogodamemoria.model


data class Card(
    val id: Int,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false,
    val points: Int = 1
) {}