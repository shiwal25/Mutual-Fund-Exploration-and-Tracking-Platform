package com.example.mutualfundexplorationandtrackingplatform.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.WatchList
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.watchListDataEntity.WatchListFundCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFundToWatchlist(crossRef: WatchListFundCrossRef)

    @Query("DELETE FROM watchlist_fund_cross_ref WHERE watchListId = :watchListId AND schemeCode = :schemeCode")
    suspend fun removeFundFromWatchlist(watchListId: Long, schemeCode: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchlist(watchlist: WatchList): Long

    @Query("SELECT * FROM watchlists")
    fun getAllWatchlists(): Flow<List<WatchList>>

    @RewriteQueriesToDropUnusedColumns
    @Query("""
        SELECT mutual_funds.* FROM mutual_funds 
        INNER JOIN watchlist_fund_cross_ref 
        ON mutual_funds.schemeCode = watchlist_fund_cross_ref.schemeCode 
        WHERE watchlist_fund_cross_ref.watchListId = :watchListId
    """)
    fun getFundsInWatchlist(watchListId: Long): Flow<List<MutualFundDetail>>

    @Query("SELECT watchListId FROM watchlist_fund_cross_ref WHERE schemeCode = :schemeCode")
    fun getWatchlistsContainingFund(schemeCode: Int): Flow<List<Long>>
}