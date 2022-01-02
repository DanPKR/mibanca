package com.danpkr.dbmodule.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    @PrimaryKey(autoGenerate = true)
    val cardId: Int? = null,
    val userid: Int,
    var Cardholder: String,
    var hashed_pan: String,
    var masked_pan: String,
    var exp_month: String,
    var exp_year: String,
    var status: String,
    val createdAt: String,
    var updatedAt: String
)
