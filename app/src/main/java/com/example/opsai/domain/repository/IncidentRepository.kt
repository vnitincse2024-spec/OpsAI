package com.example.opsai.domain.repository

import com.example.opsai.domain.model.*
import com.example.opsai.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IncidentRepository {
    fun getIncidents(): Flow<Resource<List<Incident>>>
    fun getIncidentById(id: String): Flow<Resource<Incident>>
    suspend fun createIncident(incident: Incident): Resource<Incident>
    suspend fun updateIncident(id: String, status: IncidentStatus): Resource<Incident>
    suspend fun analyzeIncident(id: String): Resource<AIAnalysis>
    suspend fun analyzeLogs(logs: String): Resource<AIAnalysis>
    fun getSimilarIncidents(id: String): Flow<Resource<List<Incident>>>
    fun getTimeline(id: String): Flow<Resource<List<IncidentTimelineEvent>>>
}
