package com.example.mutualfundexplorationandtrackingplatform.data.repository

import android.util.Log
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.MutualFundDAO
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFund
import com.example.mutualfundexplorationandtrackingplatform.data.remote.api.MutualFundApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import com.example.mutualfundexplorationandtrackingplatform.data.remote.mapper.toEntityList

class MFRepository(private val mutualFundDAO: MutualFundDAO,
                   private val mutualFundApiService: MutualFundApiService
) : MutualFundRepository {

    override fun getAllFundsStream(): Flow<List<MutualFund>> {
        return mutualFundDAO.getAllFunds()
            .onStart {
                refreshFunds()
            }
    }

    override suspend fun refreshFunds() {
        try {
            val networkFundsDto = mutualFundApiService.getLatestMutualFunds()
            val dbEntities = networkFundsDto.toEntityList()
            mutualFundDAO.insertAll(dbEntities)

        } catch (e: Exception) {
            Log.e("MFRepository", "Network fetching failed, sending data from database.", e)
        }
    }
}