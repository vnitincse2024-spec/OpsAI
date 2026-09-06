package com.example.opsai.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.opsai.domain.model.Incident
import com.example.opsai.domain.model.IncidentStatus
import com.example.opsai.domain.model.Severity

@Entity(tableName = "incidents")
data class IncidentEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val severity: String,
    val status: String,
    val category: String,
    val service: String,
    val environment: String,
    val assignedTeam: String,
    val assignedEngineer: String?,
    val slaStatus: String,
    val createdAt: Long,
    val updatedAt: Long,
    val logs: String?
) {
    fun toIncident(): Incident {
        return Incident(
            id = id,
            title = title,
            description = description,
            severity = try { Severity.valueOf(severity) } catch(e: Exception) { Severity.INFO },
            status = try { IncidentStatus.valueOf(status) } catch(e: Exception) { IncidentStatus.OPEN },
            category = category,
            service = service,
            environment = environment,
            assignedTeam = assignedTeam,
            assignedEngineer = assignedEngineer,
            slaStatus = slaStatus,
            createdAt = createdAt,
            updatedAt = updatedAt,
            logs = logs
        )
    }
}

fun Incident.toEntity(): IncidentEntity {
    return IncidentEntity(
        id = id,
        title = title,
        description = description,
        severity = severity.name,
        status = status.name,
        category = category,
        service = service,
        environment = environment,
        assignedTeam = assignedTeam,
        assignedEngineer = assignedEngineer,
        slaStatus = slaStatus,
        createdAt = createdAt,
        updatedAt = updatedAt,
        logs = logs
    )
}
