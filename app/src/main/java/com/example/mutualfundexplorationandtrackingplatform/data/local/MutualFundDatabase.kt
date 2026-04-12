package com.example.mutualfundexplorationandtrackingplatform.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.MutualFundDAO
import com.example.mutualfundexplorationandtrackingplatform.data.local.dao.WatchListDao
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.MutualFundDetail
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.WatchList
import com.example.mutualfundexplorationandtrackingplatform.data.local.entity.watchListDataEntity.WatchListFundCrossRef

@Database(entities = [MutualFundDetail::class,
    WatchList::class,
    WatchListFundCrossRef::class],
    version = 4, exportSchema = false)
abstract class MutualFundDatabase : RoomDatabase() {
    abstract fun mutualFundDAO(): MutualFundDAO
    abstract fun watchListDao(): WatchListDao

    companion object{
        @Volatile
        private var Instance: MutualFundDatabase? = null

//        fun getDatabase(context: Context): MutualFundDatabase{
//            return Instance?: synchronized(this){
//                Room.databaseBuilder(context, MutualFundDatabase::class.java, "mutualFund_database")
//                    .fallbackToDestructiveMigration().build()
//                    .also { Instance = it }
//            }
//        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Just adds the new column; existing rows default to 0 (treated as "never synced")
                database.execSQL(
                    "ALTER TABLE mutual_funds ADD COLUMN lastSyncEpoch INTEGER NOT NULL DEFAULT 0"
                )
            }
        }

        fun getDatabase(context: Context): MutualFundDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MutualFundDatabase::class.java, "mutualFund_database")
                    .addMigrations(MIGRATION_3_4)          // ← safe migration first
                    .fallbackToDestructiveMigration()      // ← only if someone skips a version
                    .build()
                    .also { Instance = it }
            }
        }
    }
}