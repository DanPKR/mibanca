package com.danpkr.mibanca.domain

import com.dts.dbmodule.Entities.User
import com.dts.dbmodule.Enums.CardStatus
import com.dts.dbmodule.Providers.DbProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigInteger
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.security.MessageDigest


private const val SALT = "&*()@#092834safdeASD"

internal object UserService {

    suspend fun CreateNewUser(name: String, lastName: String,email:String, clearPassword: String){
        return withContext(Dispatchers.IO){
            if(DbProvider.db!!.userDao().getUserByEmail(email).isNotEmpty()){
                throw Exception("Email already exists")
            }
            val newUser = User(
                nombre = name,
                apellidos = lastName,
                email = email,
                hashed_password = hashPassword(clearPassword),
                createdAt = getCurrentTime(),
                updatedAt = getCurrentTime(),
                status = CardStatus.ACTIVE.name
            )
            DbProvider.db!!.userDao().insert(newUser)
        }
    }

    suspend fun LoginUser(email:String, clearPassword: String): Int{
        return  withContext(Dispatchers.IO){
            if(DbProvider.db!!.userDao().getUserByEmail(email).isEmpty()){
                throw Exception("Account does not Exists")
            }
            val user = DbProvider.db!!.userDao().getUserByEmail(email).last()
            if (!secureComparePassword(user,clearPassword)){
                throw Exception("Wrong Password")
            }
            user.userId!!
        }
    }

    suspend fun GetUserInfo(id: Int): User{
        return withContext(Dispatchers.IO){
            val user = DbProvider.db?.userDao()?.getUserById(id)
            user!!
        }
    }


    private fun secureComparePassword( user : User, clearPassword: String): Boolean {
        val pass = hashPassword(clearPassword)
        return pass.equals(user.hashed_password)
    }

    private fun hashPassword(pass: String): String {
        val str = pass + SALT
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }

    private fun getCurrentTime(): String{
        return DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
    }
}