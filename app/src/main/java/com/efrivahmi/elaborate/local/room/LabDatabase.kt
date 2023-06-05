package com.efrivahmi.elaborate.local.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class LabDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: LabDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): LabDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    LabDatabase::class.java, "e-LABorate_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}