package com.danpkr.dbmodule.Daos

import androidx.room.*
import com.danpkr.dbmodule.Entities.Card

@Dao
interface CardDao {
    @Query("SELECT * FROM Card")
    suspend fun getAllCard(): List<Card>

    @Query("SELECT * FROM Card WHERE status = :status")
    suspend fun getCardByStatus(status: String): List<Card>

    @Insert()
    suspend fun insert(card: Card)

    @Update()
    suspend fun  update(card: Card)

    @Delete()
    suspend fun delete(card: Card)
}
