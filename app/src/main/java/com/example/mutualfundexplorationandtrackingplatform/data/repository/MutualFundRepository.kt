package com.example.mutualfundexplorationandtrackingplatform.data.repository

import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFund
import kotlinx.coroutines.flow.Flow

interface MutualFundRepository {

    fun getAllFundsStream(): Flow<List<MutualFund>>

    suspend fun refreshFunds()

}