package com.danpkr.mibanca.ui.activities.Main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danpkr.dbmodule.Entities.Card
import com.danpkr.mibanca.R

class CardAdapter (var values: List<Card> ): RecyclerView.Adapter<CardAdapter.ItemHolder>(){
    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun render(card: Card){
            view.findViewById<TextView>(R.id.cardNumber).setText(card.masked_pan)
            view.findViewById<TextView>(R.id.cardHolder).setText(card.Cardholder)
            view.findViewById<TextView>(R.id.expMonth).setText(card.exp_month)
            view.findViewById<TextView>(R.id.expYear).setText(card.exp_year)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemHolder(layoutInflater.inflate(R.layout.pay_card_view,parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(values[position])
    }

    override fun getItemCount() = values.size
}