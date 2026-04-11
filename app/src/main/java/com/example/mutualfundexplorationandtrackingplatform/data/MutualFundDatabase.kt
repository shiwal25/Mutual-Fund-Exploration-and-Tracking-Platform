package com.example.mutualfundexplorationandtrackingplatform.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mutualfundexplorationandtrackingplatform.data.dao.FundRemoteKeyDAO
import com.example.mutualfundexplorationandtrackingplatform.data.dao.MutualFundDAO
import com.example.mutualfundexplorationandtrackingplatform.data.dao.MutualFundDetailDAO
import com.example.mutualfundexplorationandtrackingplatform.data.dao.NavHistoryDAO

@Database(entities = [
    MutualFund::class,
    MutualFundDetail::class,
    NavHistory::class,
    FundRemoteKey::class],
    version = 1, exportSchema = false)
abstract class MutualFundDatabase : RoomDatabase() {
    abstract fun mutualFundDAO(): MutualFundDAO
    abstract fun mutualFundDetailDAO(): MutualFundDetailDAO
    abstract fun navHistoryDAO(): NavHistoryDAO
    abstract fun fundRemoteKeyDAO(): FundRemoteKeyDAO

    companion object{
        @Volatile
        private var Instance: MutualFundDatabase? = null

        fun getDatabase(context: Context): MutualFundDatabase{
            return Instance?: synchronized(this){
                Room.databaseBuilder(context, MutualFundDatabase::class.java, "mutualFund_database")
                    .fallbackToDestructiveMigration().build()
                    .also { Instance = it }
            }
        }
    }
}