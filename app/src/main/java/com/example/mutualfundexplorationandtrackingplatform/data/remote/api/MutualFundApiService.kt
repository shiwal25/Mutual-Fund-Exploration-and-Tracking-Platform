package com.example.mutualfundexplorationandtrackingplatform.data.remote.api

import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDTO
import com.example.mutualfundexplorationandtrackingplatform.data.remote.dto.MutualFundDetailDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MutualFundApiService {

    @GET("mf")
    suspend fun getMutualFunds(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): List<MutualFundDTO>

    @GET("mf/search")
    suspend fun searchFunds(
        @Query("q") query: String
    ): List<MutualFundDTO>

    @GET("mf/{schemeCode}/latest")
    suspend fun getLatestNav(
        @Path("schemeCode") schemeCode: Int
    ): MutualFundDetailDTO

    @GET("mf/{schemeCode}")
    suspend fun getNavHistory(
        @Path("schemeCode") schemeCode: Int,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): MutualFundDetailDTO

}