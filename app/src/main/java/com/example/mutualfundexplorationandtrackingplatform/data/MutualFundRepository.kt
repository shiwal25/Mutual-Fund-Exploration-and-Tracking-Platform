package com.example.mutualfundexplorationandtrackingplatform.data

interface MutualFundRepository {
    suspend fun getMutualFunds(): String
}