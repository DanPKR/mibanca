package com.dts.dbmodule.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
 data class User(
  @PrimaryKey(autoGenerate = true)
  val userId: Int? = null,
  var nombre: String,
  var apellidos: String,
  var email:String,
  var balance: Double = 0.0,
  var hashed_password: String,
  var status: String,
  val createdAt: String,
  var updatedAt: String
)