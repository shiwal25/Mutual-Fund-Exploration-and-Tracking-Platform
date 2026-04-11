package com.example.mutualfundexplorationandtrackingplatform.data.remote.api

import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDetailDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MutualFundApiService {

    @GET("mf/latest")
    suspend fun getLatestMutualFunds(): List<MutualFundDetailDTO>

}