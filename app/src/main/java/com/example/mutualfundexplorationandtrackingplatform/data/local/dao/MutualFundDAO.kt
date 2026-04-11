package com.example.mutualfundexplorationandtrackingplatform.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface MutualFundDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFunds(funds: List<MutualFundDetail>)


    @Query(
        """
        UPDATE mutual_funds
        SET fundHouse = :fundHouse,
            schemeType = :schemeType,
            schemeCategory = :schemeCategory,
            latestNav = :latestNav,
            latestNavDate = :latestNavDate,
            isInGrowth = :isInGrowth,
            isInDivReinvestment = :isInDivReinvestment,
            detailsIsFetched = true
        WHERE schemeCode = :schemeCode
        """
    )
    suspend fun updateFundDetails(
        schemeCode: Int?,
        fundHouse: String?,
        schemeType: String?,
        schemeCategory: String?,
        latestNav: String?,
        latestNavDate: String?,
        isInGrowth: String?,
        isInDivReinvestment: String?
    )

    @Query("SELECT * FROM mutual_funds WHERE schemeCode = :schemeCode")
    suspend fun getFundByScheme(schemeCode: Int?): MutualFundDetail?

    @Query("SELECT * FROM mutual_funds WHERE schemeCode = :schemeCode")
    fun observeFundByScheme(schemeCode: Int?): Flow<MutualFundDetail?>

}