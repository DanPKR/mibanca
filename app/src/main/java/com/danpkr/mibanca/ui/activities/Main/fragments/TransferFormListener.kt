package com.danpkr.mibanca.ui.activities.Main.fragments

interface TransferFormListener {
    fun onTransferValidated(amount: String, dest_card: String, emit_card: String, concept: String, dest_name: String)
    fun onRechargeValidated(amount: String,emit_card: String)
}