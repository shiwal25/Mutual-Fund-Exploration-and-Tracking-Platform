package com.example.mutualfundexplorationandtrackingplatform.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.MutualFundDAO
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.WatchListDao
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.WatchList
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.watchListDataEntity.WatchListFundCrossRef

@Database(entities = [MutualFundDetail::class,
    WatchList::class,
    WatchListFundCrossRef::class],
    version = 3, exportSchema = false)
abstract class MutualFundDatabase : RoomDatabase() {
    abstract fun mutualFundDAO(): MutualFundDAO
    abstract fun watchListDao(): WatchListDao

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