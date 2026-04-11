package com.example.mutualfundexplorationandtrackingplatform.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mutualfundexplorationandtrackingplatform.data.MutualFund

@Dao
interface MutualFundDAO {

    @Query("SELECT * FROM funds ORDER BY schemeName ASC")
    fun getAllFundsPaged(): PagingSource<Int, MutualFund>

    @Query("SELECT * FROM funds WHERE schemeName LIKE '%' || :query || '%'")
    fun searchFunds(query: String): PagingSource<Int, MutualFund>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(funds: List<MutualFund>)

    @Query("DELETE FROM funds")
    suspend fun clearAll()
}
