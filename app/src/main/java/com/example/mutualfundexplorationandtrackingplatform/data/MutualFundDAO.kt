package com.example.mutualfundexplorationandtrackingplatform.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MutualFundDAO {

    @Query("Select * from mutualFunds")
    fun getAllMutualFunds(): Flow<List<MutualFund>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMutualFunds(funds: List<MutualFund>)
}