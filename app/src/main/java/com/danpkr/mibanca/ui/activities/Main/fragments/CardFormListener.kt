package com.danpkr.mibanca.ui.activities.Main.fragments

interface CardFormListener {
    fun onCardAccepted(pan: String, expDate: String, cvv: String, cardHolder: String)
}