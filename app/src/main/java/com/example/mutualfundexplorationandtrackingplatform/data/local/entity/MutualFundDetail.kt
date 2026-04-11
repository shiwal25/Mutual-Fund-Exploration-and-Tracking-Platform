package com.example.mutualfundexplorationandtrackingplatform.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mutual_funds")
data class MutualFundDetail(
    @PrimaryKey val schemeCode: Int?,
    val schemeName: String?,
    val fundHouse: String?,
    val schemeType: String?,
    val schemeCategory: String?,
    val latestNav: String?,
    val latestNavDate: String?,
    val isInGrowth:String?,
    val isInDivReinvestment:String?,
    val detailsIsFetched: Boolean = false
)