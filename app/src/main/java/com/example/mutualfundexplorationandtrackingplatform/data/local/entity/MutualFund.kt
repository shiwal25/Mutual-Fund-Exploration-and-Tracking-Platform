package com.example.mutualfundexplorationandtrackingplatform.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mutual_fund_details")
data class MutualFund(
    @PrimaryKey val schemeCode: Int?,
    val schemeName: String?,
    val fundHouse: String?,
    val schemeType: String?,
    val schemeCategory: String?,
    val latestNav: String?,
    val latestNavDate: String?,
    val isInGrowth:String?,
    val isInDivReinvestment:String?,
    val fetchedAt: Long = System.currentTimeMillis()
)