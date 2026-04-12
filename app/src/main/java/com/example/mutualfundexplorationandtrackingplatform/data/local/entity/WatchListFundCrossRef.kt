package com.example.mutualfundexplorationandtrackingplatform.data.local.entity

import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "watchlist_fund_cross_ref",
    primaryKeys = ["watchListId", "schemeCode"],
indices = [
    Index(value = ["schemeCode"]),
Index(value = ["watchListId"])
])
data class WatchListFundCrossRef(
    val watchListId:Long,
    val schemeCode: Int
)
