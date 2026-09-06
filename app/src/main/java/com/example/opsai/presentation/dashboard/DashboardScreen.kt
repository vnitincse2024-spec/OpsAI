package com.example.opsai.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onNavigateToIncidents: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("OpsAI Dashboard") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("System Status", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                StatusCard()
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard("Critical", "3", Icons.Default.Warning, Color.Red, Modifier.weight(1f))
                    StatCard("Open", "12", Icons.Default.Info, Color.Blue, Modifier.weight(1f))
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard("MTTR", "42m", Icons.Default.Timer, Color.Green, Modifier.weight(1f))
                    StatCard("SLA", "98.5%", Icons.Default.CheckCircle, Color.Magenta, Modifier.weight(1f))
                }
            }

            item {
                Text("AI Operational Insights", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                AIInsightCard()
            }

            item {
                Button(
                    onClick = onNavigateToIncidents,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View All Incidents")
                }
            }
        }
    }
}

@Composable
fun StatusCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.CloudDone, contentDescription = null, tint = Color.Green)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("All Systems Operational", style = MaterialTheme.typography.bodyLarge)
                Text("Last updated: 2 mins ago", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = color)
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.headlineMedium)
            Text(label, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun AIInsightCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("AI Insight", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Increased latency detected in 'payment-service'. Possible connection pool exhaustion.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
