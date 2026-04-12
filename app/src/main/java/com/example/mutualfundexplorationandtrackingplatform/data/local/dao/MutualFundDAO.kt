package com.example.mutualfundexplorationandtrackingplatform.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface MutualFundDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFunds(funds: List<MutualFundDetail>)


    @Query(
        """
        UPDATE mutual_funds
        SET fundHouse = :fundHouse,
            schemeType = :schemeType,
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
//        schemeCategory: String?,
        latestNav: String?,
        latestNavDate: String?,
        isInGrowth: String?,
        isInDivReinvestment: String?
    )

    @Query("SELECT * FROM mutual_funds WHERE schemeCode = :schemeCode")
    suspend fun getFundByScheme(schemeCode: Int?): MutualFundDetail?

    @Query("SELECT * FROM mutual_funds WHERE schemeCode = :schemeCode")
    fun observeFundByScheme(schemeCode: Int?): Flow<MutualFundDetail?>


    @Query("SELECT * FROM mutual_funds LIMIT :limit OFFSET :offset")
    suspend fun getAllFunds(limit: Int, offset: Int): List<MutualFundDetail>

    @Query("SELECT * FROM mutual_funds WHERE schemeCategory = :category ORDER BY schemeCode ASC")
    suspend fun getFundsByCategory(category: String): List<MutualFundDetail>

    @Query("SELECT * FROM mutual_funds WHERE schemeCategory = :category ORDER BY schemeCode ASC")
    fun observeFundsByCategory(category: String): Flow<List<MutualFundDetail>>

    /** Stamp the sync time for a single fund after a detail fetch. */
    @Query("UPDATE mutual_funds SET lastSyncEpoch = :epoch WHERE schemeCode = :schemeCode")
    suspend fun updateSyncTime(schemeCode: Int?, epoch: Long)

    /**
     * Stamp all funds in a category after a category fetch.
     * Works for both new inserts (insertFunds + IGNORE won't update sync time) and
     * re-synced existing rows.
     */
    @Query("UPDATE mutual_funds SET lastSyncEpoch = :epoch WHERE schemeCategory = :category")
    suspend fun updateCategorySyncTime(category: String, epoch: Long)

    /**
     * The "freshness" of a category is limited by its oldest-synced member.
     * If even one fund has lastSyncEpoch = 0, the whole category is considered stale.
     */
    @Query("SELECT MIN(lastSyncEpoch) FROM mutual_funds WHERE schemeCategory = :category")
    suspend fun getOldestSyncForCategory(category: String): Long?

}