package com.danpkr.dbmodule.Entities

import androidx.room.Embedded
import androidx.room.Relation
import com.dts.dbmodule.Entities.User

data class UserPayments(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userCreatorId"
    )
    val payments: List<Payment>
)
