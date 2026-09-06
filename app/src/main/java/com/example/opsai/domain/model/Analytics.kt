package com.example.opsai.domain.model

data class Analytics(
    val mttr: Double,
    val incidentCount: Int,
    val criticalIncidents: Int,
    val slaCompliance: Double,
    val incidentsOverTime: List<DataPoint>,
    val incidentsBySeverity: Map<Severity, Int>,
    val incidentsByCategory: Map<String, Int>
)

data class DataPoint(
    val label: String,
    val value: Float
)
