package com.danpkr.dbmodule.Daos

import androidx.room.*
import com.danpkr.dbmodule.Entities.Payment

@Dao
interface PaymentDao {

    @Query("SELECT * FROM Payment")
    suspend fun getAllPayments(): List<Payment>

    @Insert()
    suspend fun insert(payment: Payment)

    @Update()
    suspend fun  update(payment: Payment)

    @Delete()
    suspend fun delete(payment: Payment)
}