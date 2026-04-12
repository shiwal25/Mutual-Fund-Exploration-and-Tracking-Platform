package com.example.mutualfundexplorationandtrackingplatform.data.local.entity.watchListDataEntity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.WatchList

data class WatchListWithFunds(
    @Embedded val watchlist: WatchList,

    @Relation(
        parentColumn = "watchListId",
        entityColumn = "schemeCode",
        associateBy = Junction(WatchListFundCrossRef::class)
    )
    val funds: List<MutualFundDetail>
)