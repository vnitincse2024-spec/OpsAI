package com.example.opsai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [IncidentEntity::class], version = 1, exportSchema = false)
abstract class OpsAIDatabase : RoomDatabase() {
    abstract val incidentDao: IncidentDao
}
