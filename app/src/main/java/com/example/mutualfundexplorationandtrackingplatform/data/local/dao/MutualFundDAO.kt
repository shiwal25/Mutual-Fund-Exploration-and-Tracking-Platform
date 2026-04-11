package com.example.mutualfundexplorationandtrackingplatform.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFund
import kotlinx.coroutines.flow.Flow

@Dao
interface MutualFundDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(funds: List<MutualFund>)

    @Query("SELECT * FROM mutual_fund_details")
    fun getAllFunds(): Flow<List<MutualFund>>

//    @Query("SELECT * FROM mutual_fund_details WHERE schemeCode = :schemeCode")
//    suspend fun getFundByCode(schemeCode: Int): MutualFund?

//    @Query("DELETE FROM mutual_fund_details")
//    suspend fun clearAll()
}