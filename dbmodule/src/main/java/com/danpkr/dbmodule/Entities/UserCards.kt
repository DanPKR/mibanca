package com.danpkr.dbmodule.Entities

import androidx.room.Embedded
import androidx.room.Relation
import com.dts.dbmodule.Entities.User

data class UserCards(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userid"
    )
    val cards: List<Card>
)
