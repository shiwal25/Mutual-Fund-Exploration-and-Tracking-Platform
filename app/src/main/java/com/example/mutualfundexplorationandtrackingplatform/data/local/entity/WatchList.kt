package com.example.mutualfundexplorationandtrackingplatform.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlists")
data class WatchList(
    @PrimaryKey(autoGenerate = true)
    val watchListId: Long = 0,
    val name: String
)
