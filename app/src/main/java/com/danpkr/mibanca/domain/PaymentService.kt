package com.danpkr.mibanca.domain

import com.danpkr.dbmodule.Entities.Payment
import com.dts.dbmodule.Providers.DbProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object PaymentService {

    suspend fun createPayment(userId: Int,amount: String,emit_card: String, dest_card: String, dest_name: String, concept: String){
        return withContext(Dispatchers.IO){
            val newPay = Payment(
                userCreatorId = userId,
                emit_card_no =  emit_card,
                dest_card_no = dest_card,
                dest_name = dest_name,
                amount = amount,
                concept = concept,
                transaction_date = getCurrentTime(),
                createdAt = getCurrentTime(),
                updatedAt = getCurrentTime()
            )
            DbProvider.db?.paymentDao()?.insert(newPay)
        }
    }

    suspend fun makeRecharge(userId: Int, amount: String, emit_card: String){
        val user = DbProvider.db?.userDao()?.getUserById(userId)
        createPayment(userId,
            amount,
            emit_card,
            "Cuenta MiBanca",
            user!!.apellidos + " " + user!!.nombre, "Abono a Cuenta")
        user.balance += amount.toDouble()
        DbProvider.db?.userDao()?.update(user)
    }

    suspend fun getPayments(userId: Int): List<Payment>{
        return withContext(Dispatchers.IO){
            val q = DbProvider.db?.userDao()?.getUserWithPayments(userId)
            q?.payments?.asReversed() ?: emptyList()
        }
    }

    private fun getCurrentTime(): String{
        return DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
    }
}