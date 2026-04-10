package com.example.mutualfundexplorationandtrackingplatform.data

import com.example.mutualfundexplorationandtrackingplatform.network.MutualFundApiService
import kotlinx.coroutines.flow.Flow

class MFRepository(private val mutualFundDAO: MutualFundDAO, private val mutualFundApiService: MutualFundApiService) : MutualFundRepository{
    /* yaha pe we will check if data fetch successfully to send that data otherwise send the room DB cached data*/

    override fun getAllMutualFunds(): Flow<List<MutualFund>> {
        return mutualFundDAO.getAllMutualFunds()
    }

    override suspend fun refreshMutualFunds() {
        try {
            // fetching data from Api
            val networkData = mutualFundApiService.getMutualFunds()
            // Insert into Room. Because Room returns a Flow,
            // inserting new data will automatically update your UI!
            mutualFundDAO.insertMutualFunds(networkData)
        } catch (e: Exception) {
            // Network failed. Handle the error (e.g., logging).
            // The ViewModel will still receive the cached Room data from the Flow above.
        }
    }
}