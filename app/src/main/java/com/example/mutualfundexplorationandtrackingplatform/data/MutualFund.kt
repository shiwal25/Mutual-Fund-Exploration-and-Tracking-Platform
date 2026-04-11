package com.example.mutualfundexplorationandtrackingplatform.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "mutual_funds")
data class MutualFundDetail(
    @PrimaryKey val schemeCode: Int,
    val fundHouse: String,
    val schemeType: String,
    val schemeCategory: String,
    val schemeName: String,
    val latestNav: String,       // e.g. "29.21000"
    val latestNavDate: String,   // e.g. "10-04-2026"
    val fetchedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "funds")
data class MutualFund(
    @PrimaryKey val schemeCode: Int,
    val schemeName: String,
    val isinGrowth: String?,
    val isinDivReinvestment: String?
)

@Entity(
    tableName = "nav_history",
    primaryKeys = ["schemeCode", "date"]
)
data class NavHistory(
    val schemeCode: Int,
    val date: String,
    val nav: Double
)

@Entity(tableName = "fund_remote_keys")
data class FundRemoteKey(
    @PrimaryKey val schemeCode: Int,
    val prevOffset: Int?,
    val nextOffset: Int?
)