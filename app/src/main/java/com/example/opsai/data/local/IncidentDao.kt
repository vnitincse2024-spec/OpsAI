package com.example.opsai.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IncidentDao {
    @Query("SELECT * FROM incidents ORDER BY createdAt DESC")
    fun getIncidents(): Flow<List<IncidentEntity>>

    @Query("SELECT * FROM incidents WHERE id = :id")
    suspend fun getIncidentById(id: String): IncidentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncidents(incidents: List<IncidentEntity>)

    @Query("DELETE FROM incidents")
    suspend fun clearIncidents()
}
