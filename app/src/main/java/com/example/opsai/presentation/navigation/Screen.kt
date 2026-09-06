package com.example.opsai.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object IncidentList : Screen("incident_list")
    object IncidentDetail : Screen("incident_detail/{incidentId}") {
        fun createRoute(incidentId: String) = "incident_detail/$incidentId"
    }
    object CreateIncident : Screen("create_incident")
    object LogAnalyzer : Screen("log_analyzer")
    object KnowledgeBase : Screen("knowledge_base")
    object Analytics : Screen("analytics")
    object Profile : Screen("profile")
}
