package com.example.mutualfundexplorationandtrackingplatform.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface MutualFundDetailDAO {
    @Query("SELECT * FROM mutual_funds WHERE schemeCode = :code")
    fun getFundDetail(code: Int): Flow<MutualFundDetail?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(detail: MutualFundDetail)
}