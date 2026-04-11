package com.example.mutualfundexplorationandtrackingplatform.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.FundRemoteKey

@Dao
interface FundRemoteKeyDAO {
    @Query("SELECT * FROM fund_remote_keys WHERE schemeCode = :code")
    suspend fun getKey(code: Int): FundRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<FundRemoteKey>)

    @Query("DELETE FROM fund_remote_keys")
    suspend fun clearAll()
}