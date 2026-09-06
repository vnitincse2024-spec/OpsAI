package com.example.opsai.data.remote.dto

import com.example.opsai.domain.model.*
import com.google.gson.annotations.SerializedName

data class IncidentDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("severity") val severity: String,
    @SerializedName("status") val status: String,
    @SerializedName("category") val category: String,
    @SerializedName("service") val service: String,
    @SerializedName("environment") val environment: String,
    @SerializedName("assigned_team") val assigned_team: String,
    @SerializedName("assigned_engineer") val assigned_engineer: String? = null,
    @SerializedName("sla_status") val sla_status: String,
    @SerializedName("created_at") val created_at: Long,
    @SerializedName("updated_at") val updated_at: Long,
    @SerializedName("ai_analysis") val ai_analysis: AIAnalysisDto? = null,
    @SerializedName("logs") val logs: String? = null
) {
    fun toIncident(): Incident {
        return Incident(
            id = id,
            title = title,
            description = description,
            severity = try { Severity.valueOf(severity.uppercase()) } catch(e: Exception) { Severity.INFO },
            status = try { IncidentStatus.valueOf(status.uppercase()) } catch(e: Exception) { IncidentStatus.OPEN },
            category = category,
            service = service,
            environment = environment,
            assignedTeam = assigned_team,
            assignedEngineer = assigned_engineer,
            slaStatus = sla_status,
            createdAt = created_at,
            updatedAt = updated_at,
            aiAnalysis = ai_analysis?.toAIAnalysis(),
            logs = logs
        )
    }
}

data class AIAnalysisDto(
    @SerializedName("root_cause") val root_cause: String,
    @SerializedName("confidence") val confidence: Int,
    @SerializedName("evidence") val evidence: List<String>,
    @SerializedName("recommended_actions") val recommended_actions: List<String>,
    @SerializedName("suggested_severity") val suggested_severity: String? = null,
    @SerializedName("suggested_category") val suggested_category: String? = null
) {
    fun toAIAnalysis(): AIAnalysis {
        return AIAnalysis(
            rootCause = root_cause,
            confidence = confidence,
            evidence = evidence,
            recommendedActions = recommended_actions,
            suggestedSeverity = suggested_severity?.let { try { Severity.valueOf(it.uppercase()) } catch(e: Exception) { null } },
            suggestedCategory = suggested_category
        )
    }
}
