package com.danpkr.mibanca.domain

import com.danpkr.dbmodule.Entities.Card
import com.dts.dbmodule.Enums.CardStatus
import com.dts.dbmodule.Providers.DbProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigInteger
import java.security.MessageDigest
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private const val SALT = "&*()@#092834safdeASD"

object CardService {
    suspend fun createNewCard(userId: Int, pan: String, expDate: String, cardHolder: String){
        return withContext(Dispatchers.IO){
            val newCard = Card(
                userid = userId,
                masked_pan = maskPan(pan),
                Cardholder = cardHolder,
                hashed_pan = hashText(pan),
                exp_month = expDate.split("/")[0],
                exp_year =  expDate.split("/")[1],
                status = CardStatus.ACTIVE.name,
                createdAt = getCurrentTime(),
                updatedAt = getCurrentTime()
            )
            DbProvider.db?.cardDao()?.insert(newCard)
        }
    }

    suspend fun getCards(userId: Int):List<Card>{
        return withContext(Dispatchers.IO){
            val q = DbProvider.db?.userDao()?.getUserWithCards(userId)
            q?.cards ?: emptyList()
        }
    }

    private fun maskPan(input : String): String{
        return input.replace(Regex("\\w(?=\\w{4})"),"*")
    }

    private fun hashText(pass: String): String {
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