package com.example.mutualfundexplorationandtrackingplatform.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.MutualFundDAO
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFund

@Database(entities = [MutualFund::class,],
    version = 1, exportSchema = false)
abstract class MutualFundDatabase : RoomDatabase() {
    abstract fun mutualFundDAO(): MutualFundDAO

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