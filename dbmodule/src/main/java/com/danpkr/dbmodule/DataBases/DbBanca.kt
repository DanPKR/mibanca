package com.dts.dbmodule.DataBases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danpkr.dbmodule.Daos.CardDao
import com.danpkr.dbmodule.Daos.PaymentDao
import com.danpkr.dbmodule.Entities.Card
import com.danpkr.dbmodule.Entities.Payment
import com.dts.dbmodule.Daos.UserDao
import com.dts.dbmodule.Entities.User

@Database(
    entities = [User::class, Card::class, Payment::class],
    version = 3
)
 abstract class DbBanca : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun cardDao(): CardDao
    abstract fun paymentDao(): PaymentDao
}