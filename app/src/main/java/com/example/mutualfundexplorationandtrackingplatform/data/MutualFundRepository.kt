package com.example.mutualfundexplorationandtrackingplatform.data

import kotlinx.coroutines.flow.Flow

interface MutualFundRepository {
    fun getAllMutualFunds(): Flow<List<MutualFund>>

    suspend fun refreshMutualFunds()
}