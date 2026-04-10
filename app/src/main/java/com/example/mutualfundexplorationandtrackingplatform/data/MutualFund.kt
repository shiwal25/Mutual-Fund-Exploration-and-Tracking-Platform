package com.example.mutualfundexplorationandtrackingplatform.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "mutualFunds")
data class MutualFund(

    @PrimaryKey(true)
    val id: Int,
    val name:String
)
