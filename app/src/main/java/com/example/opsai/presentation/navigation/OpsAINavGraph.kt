package com.example.opsai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.opsai.presentation.auth.LoginScreen
import com.example.opsai.presentation.dashboard.DashboardScreen
import com.example.opsai.presentation.incident_list.IncidentListScreen
import com.example.opsai.presentation.incident_detail.IncidentDetailScreen
import com.example.opsai.presentation.create_incident.CreateIncidentScreen
import com.example.opsai.presentation.log_analyzer.LogAnalyzerScreen
import com.example.opsai.presentation.knowledge_base.KnowledgeBaseScreen
import com.example.opsai.presentation.analytics.AnalyticsScreen
import com.example.opsai.presentation.profile.ProfileScreen

@Composable
fun OpsAINavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(onNavigateToIncidents = {
                navController.navigate(Screen.IncidentList.route)
            })
        }
        composable(Screen.IncidentList.route) {
            IncidentListScreen(onIncidentClick = { id ->
                navController.navigate(Screen.IncidentDetail.createRoute(id))
            })
        }
        composable(Screen.IncidentDetail.route) { backStackEntry ->
            val incidentId = backStackEntry.arguments?.getString("incidentId") ?: ""
            IncidentDetailScreen(incidentId = incidentId)
        }
        composable(Screen.CreateIncident.route) {
            CreateIncidentScreen(onIncidentCreated = {
                navController.popBackStack()
            })
        }
        composable(Screen.LogAnalyzer.route) {
            LogAnalyzerScreen()
        }
        composable(Screen.KnowledgeBase.route) {
            KnowledgeBaseScreen()
        }
        composable(Screen.Analytics.route) {
            AnalyticsScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen(onLogout = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0)
                }
            })
        }
    }
}
