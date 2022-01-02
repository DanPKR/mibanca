package com.danpkr.dbmodule.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Payment(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val userCreatorId: Int,
    val emit_card_no: String,
    val dest_card_no: String,
    val dest_name: String,
    val amount: String,
    val concept: String,
    val transaction_date: String,
    val createdAt: String,
    var updatedAt: String
)
