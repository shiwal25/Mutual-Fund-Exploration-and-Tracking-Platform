package com.example.mutualfundexplorationandtrackingplatform.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mutualfundexplorationandtrackingplatform.data.NavHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface NavHistoryDAO {
    @Query("SELECT * FROM nav_history WHERE schemeCode = :code ORDER BY date ASC")
    fun getNavHistory(code: Int): Flow<List<NavHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<NavHistory>)

    @Query("DELETE FROM nav_history WHERE schemeCode = :code")
    suspend fun clearForFund(code: Int)
}