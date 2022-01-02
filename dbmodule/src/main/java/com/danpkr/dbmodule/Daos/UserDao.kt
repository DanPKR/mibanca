package com.dts.dbmodule.Daos

import androidx.room.*
import com.danpkr.dbmodule.Entities.UserCards
import com.danpkr.dbmodule.Entities.UserPayments
import com.dts.dbmodule.Entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM User WHERE status = :status")
    suspend fun getUserByStatus(status: String): List<User>

    @Query("SELECT * FROM User WHERE userId = :id")
    suspend fun getUserById(id: Int): User

    @Query("SELECT * FROM User WHERE email = :email")
    suspend fun getUserByEmail(email: String): List<User>

    @Transaction
    @Query("SELECT * FROM User WHERE userId = :id")
    suspend fun getUserWithCards(id: Int): UserCards

    @Transaction
    @Query("SELECT * FROM User WHERE userId = :id")
    suspend fun getUserWithPayments(id: Int): UserPayments

    @Insert()
    suspend fun insert(user: User)

    @Update()
    suspend fun  update(user: User)

    @Delete()
    suspend fun delete(user: User)
}