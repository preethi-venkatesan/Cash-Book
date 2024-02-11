package com.example.cashbook

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CashItems::class], version = 1 )
abstract class CashDatabase : RoomDatabase() {

    abstract fun getCashDao(): CashDao

    companion object {
        @Volatile
        private var INSTANCE: CashDatabase? = null

        fun getDatabase(context: Context): CashDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CashDatabase::class.java,
                    "cash_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
