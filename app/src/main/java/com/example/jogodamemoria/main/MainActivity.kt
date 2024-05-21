package com.example.jogodamemoria.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.GridView
import com.example.jogodamemoria.R
import com.example.jogodamemoria.databinding.ActivityMainBinding
import com.example.jogodamemoria.databinding.ActivitySplashBinding
import com.example.jogodamemoria.model.Card

class MainActivity : AppCompatActivity() {

    private lateinit var cards: MutableList<Card>
    private lateinit var adapter: CardAdapter
    private lateinit var binding: ActivityMainBinding
    private var selectedCardPosition: Int? = null
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCards()
        startListeners()

    }

    private fun startListeners() {
        binding.gridView.setOnItemClickListener { _, view, position, _ ->
            val card = cards[position]

            if (!card.isFaceUp && selectedCardPosition == null) {
                card.isFaceUp = true
                selectedCardPosition = position
                adapter.notifyDataSetChanged()
            } else if (!card.isFaceUp && selectedCardPosition != null) {
                card.isFaceUp = true
                adapter.notifyDataSetChanged()

                val selectedCard = cards[selectedCardPosition!!]

                if (card.id == selectedCard.id) {
                    card.isMatched = true
                    selectedCard.isMatched = true
                    score += card.points // Adiciona pontos ao placar
                } else {
                    Handler().postDelayed({
                        card.isFaceUp = false
                        selectedCard.isFaceUp = false
                        adapter.notifyDataSetChanged()
                    }, 1000)
                }

                selectedCardPosition = null
                updateScore()
            }
        }

        binding.btnRestart.setOnClickListener {
            resetGame()
        }

    }

    private fun startCards() {
        cards = createCards()

        adapter = CardAdapter(this, cards)
        binding.gridView.adapter = adapter

    }

    private fun createCards(): MutableList<Card> {
        val imageIds = mutableListOf(
            R.drawable.card1, R.drawable.card2, R.drawable.card3,
            R.drawable.card4, R.drawable.card5, R.drawable.card6
        )

        imageIds.addAll(imageIds)
        imageIds.shuffle()

        return imageIds.map { Card(it) }.toMutableList()
    }

    private fun resetGame() {
        cards = createCards().shuffled().toMutableList()
        score = 0
        updateScore()
        adapter = CardAdapter(this, cards)
        binding.gridView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun updateScore() {
        binding.txtPontos.text = "$score"
    }
}

