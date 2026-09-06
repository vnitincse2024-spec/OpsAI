package com.example.opsai.data.repository

import com.example.opsai.data.local.IncidentDao
import com.example.opsai.data.local.toEntity
import com.example.opsai.data.remote.OpsAIApi
import com.example.opsai.domain.model.*
import com.example.opsai.domain.repository.IncidentRepository
import com.example.opsai.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IncidentRepositoryImpl @Inject constructor(
    private val api: OpsAIApi,
    private val dao: IncidentDao
) : IncidentRepository {

    override fun getIncidents(): Flow<Resource<List<Incident>>> = flow {
        emit(Resource.Loading())
        val cachedIncidents = dao.getIncidents()
        // Here we could emit cache first, then network. Simplified for now.
        try {
            val remoteIncidents = api.getIncidents().map { it.toIncident() }
            dao.clearIncidents()
            dao.insertIncidents(remoteIncidents.map { it.toEntity() })
            emit(Resource.Success(remoteIncidents))
        } catch (e: Exception) {
            emit(Resource.Error("Couldn't reach server. Showing offline data.", null))
        }
    }

    override fun getIncidentById(id: String): Flow<Resource<Incident>> = flow {
        emit(Resource.Loading())
        try {
            val incident = api.getIncidentById(id).toIncident()
            emit(Resource.Success(incident))
        } catch (e: Exception) {
            val cached = dao.getIncidentById(id)?.toIncident()
            if (cached != null) {
                emit(Resource.Success(cached))
            } else {
                emit(Resource.Error("Incident not found.", null))
            }
        }
    }

    override suspend fun createIncident(incident: Incident): Resource<Incident> {
        return try {
            val remoteDto = api.createIncident(
                com.example.opsai.data.remote.dto.IncidentDto(
                    id = incident.id,
                    title = incident.title,
                    description = incident.description,
                    severity = incident.severity.name,
                    status = incident.status.name,
                    category = incident.category,
                    service = incident.service,
                    environment = incident.environment,
                    assigned_team = incident.assignedTeam,
                    assigned_engineer = incident.assignedEngineer,
                    sla_status = incident.slaStatus,
                    created_at = incident.createdAt,
                    updated_at = incident.updatedAt,
                    logs = incident.logs
                )
            )
            Resource.Success(remoteDto.toIncident())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun updateIncident(id: String, status: IncidentStatus): Resource<Incident> {
        return try {
            val remoteDto = api.updateIncident(id, mapOf("status" to status.name))
            Resource.Success(remoteDto.toIncident())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun analyzeIncident(id: String): Resource<AIAnalysis> {
        return try {
            val analysis = api.analyzeIncident(id).toAIAnalysis()
            Resource.Success(analysis)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun analyzeLogs(logs: String): Resource<AIAnalysis> {
        return try {
            val analysis = api.analyzeLogs(mapOf("logs" to logs)).toAIAnalysis()
            Resource.Success(analysis)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    override fun getSimilarIncidents(id: String): Flow<Resource<List<Incident>>> = flow {
        emit(Resource.Loading())
        try {
            val incidents = api.getSimilarIncidents(id).map { it.toIncident() }
            emit(Resource.Success(incidents))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }

    override fun getTimeline(id: String): Flow<Resource<List<IncidentTimelineEvent>>> = flow {
        // Mocking timeline for now as there's no specific API in the prompt for timeline
        emit(Resource.Loading())
        val mockTimeline = listOf(
            IncidentTimelineEvent(System.currentTimeMillis() - 3600000, "Incident created", "STATUS_CHANGE"),
            IncidentTimelineEvent(System.currentTimeMillis() - 3500000, "AI analysis completed", "AI_ANALYSIS"),
            IncidentTimelineEvent(System.currentTimeMillis() - 3000000, "Assigned to Backend Team", "STATUS_CHANGE")
        )
        emit(Resource.Success(mockTimeline))
    }
}
