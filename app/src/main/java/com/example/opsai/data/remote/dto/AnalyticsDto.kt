package com.example.opsai.data.remote.dto

import com.example.opsai.domain.model.Analytics
import com.example.opsai.domain.model.DataPoint
import com.example.opsai.domain.model.Severity
import com.google.gson.annotations.SerializedName

data class AnalyticsDto(
    @SerializedName("mttr") val mttr: Double,
    @SerializedName("incident_count") val incident_count: Int,
    @SerializedName("critical_incidents") val critical_incidents: Int,
    @SerializedName("sla_compliance") val sla_compliance: Double,
    @SerializedName("incidents_over_time") val incidents_over_time: List<DataPointDto>,
    @SerializedName("incidents_by_severity") val incidents_by_severity: Map<String, Int>,
    @SerializedName("incidents_by_category") val incidents_by_category: Map<String, Int>
) {
    fun toAnalytics(): Analytics {
        return Analytics(
            mttr = mttr,
            incidentCount = incident_count,
            criticalIncidents = critical_incidents,
            slaCompliance = sla_compliance,
            incidentsOverTime = incidents_over_time.map { it.toDataPoint() },
            incidentsBySeverity = incidents_by_severity.mapKeys { 
                try { Severity.valueOf(it.key.uppercase()) } catch(e: Exception) { Severity.INFO } 
            },
            incidentsByCategory = incidents_by_category
        )
    }
}

data class DataPointDto(
    @SerializedName("label") val label: String,
    @SerializedName("value") val value: Float
) {
    fun toDataPoint(): DataPoint {
        return DataPoint(label = label, value = value)
    }
}
