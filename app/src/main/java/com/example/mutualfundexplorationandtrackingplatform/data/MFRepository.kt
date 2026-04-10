package com.example.mutualfundexplorationandtrackingplatform.data

import com.example.mutualfundexplorationandtrackingplatform.network.MutualFundApiService

class MFRepository(private val mutualFundDAO: MutualFundDAO, private val mutualFundApiService: MutualFundApiService) : MutualFundRepository{
    /* yaha pe we will check if data fetch successfully to send that data otherwise send the room DB cached data*/
    override suspend fun getMutualFunds(): String {
        TODO("Not yet implemented")
    }
}