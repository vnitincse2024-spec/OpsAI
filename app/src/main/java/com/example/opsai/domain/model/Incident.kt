package com.example.opsai.domain.model

import java.util.Date

data class Incident(
    val id: String,
    val title: String,
    val description: String,
    val severity: Severity,
    val status: IncidentStatus,
    val category: String,
    val service: String,
    val environment: String,
    val assignedTeam: String,
    val assignedEngineer: String?,
    val slaStatus: String,
    val createdAt: Long,
    val updatedAt: Long,
    val aiAnalysis: AIAnalysis? = null,
    val logs: String? = null
)

data class AIAnalysis(
    val rootCause: String,
    val confidence: Int,
    val evidence: List<String>,
    val recommendedActions: List<String>,
    val suggestedSeverity: Severity? = null,
    val suggestedCategory: String? = null
)

data class IncidentTimelineEvent(
    val timestamp: Long,
    val message: String,
    val type: String // e.g., "STATUS_CHANGE", "AI_ANALYSIS", "COMMENT"
)
