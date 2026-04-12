package com.example.mutualfundexplorationandtrackingplatform.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class WatchListWithFunds(
    @Embedded val watchlist: WatchList,

    @Relation(
        parentColumn = "watchListId",
        entityColumn = "schemeCode",
        associateBy = Junction(WatchListFundCrossRef::class)
    )
    val funds: List<MutualFundDetail>
)
