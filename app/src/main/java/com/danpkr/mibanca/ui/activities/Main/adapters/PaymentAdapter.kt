package com.danpkr.mibanca.ui.activities.Main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danpkr.dbmodule.Entities.Payment
import com.danpkr.mibanca.R

class PaymentAdapter (var values: List<Payment> ): RecyclerView.Adapter<PaymentAdapter.ItemHolder>(){
    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun render(payment: Payment){
            view.findViewById<TextView>(R.id.tvTransfer).text = payment.dest_name
            view.findViewById<TextView>(R.id.tvAmount).text = "$${String.format("%,.2f",payment.amount.toFloat())}"
            view.findViewById<TextView>(R.id.tvDate).text = payment.transaction_date.split(" ")[0]
            view.findViewById<TextView>(R.id.tvConcept).text = payment.concept
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemHolder(layoutInflater.inflate(R.layout.payment_list_element_view,parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(values[position])
    }

    override fun getItemCount() = values.size
}