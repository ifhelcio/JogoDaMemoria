package com.example.jogodamemoria.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.jogodamemoria.R
import com.example.jogodamemoria.model.Card

class CardAdapter(private val context: Context, private val cards: List<Card>) : BaseAdapter() {

    override fun getCount(): Int = cards.size

    override fun getItem(position: Int): Any = cards[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val card = getItem(position) as Card
        val view: View =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false)
        val cardImageView: ImageView = view.findViewById(R.id.cardImageView)

        if (card.isFaceUp) {
            cardImageView.setImageResource(card.id)
        } else {
            cardImageView.setImageResource(R.drawable.card_back)
        }

        return view
    }
}
