package com.dts.dbmodule.Providers

import android.content.Context
import androidx.room.Room
import com.dts.dbmodule.DataBases.DbBanca

object DbProvider {
    var db: DbBanca? = null
    get() {
        if (field == null)
            //Si el modulo no fue inicializado arrojamos una excepcion
            throw Exception("Db Module Not Initialized")
        return field
    }

    //Incializamos el Modulo
    fun init(ctx: Context){
        db = Room.databaseBuilder(ctx,
        DbBanca::class.java,
        "DbBanca").build()
    }
}