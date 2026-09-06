package com.example.opsai.presentation.incident_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.opsai.domain.model.Incident
import com.example.opsai.domain.model.IncidentStatus
import com.example.opsai.domain.model.Severity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentListScreen(onIncidentClick: (String) -> Unit) {
    // Mock data for UI demonstration
    val incidents = listOf(
        Incident("INC-102", "Database high latency", "DB connection pool issues", Severity.CRITICAL, IncidentStatus.OPEN, "Database", "PaymentService", "Production", "Backend", "John Doe", "Breached", System.currentTimeMillis(), System.currentTimeMillis()),
        Incident("INC-103", "Auth service timeout", "Users unable to login", Severity.HIGH, IncidentStatus.INVESTIGATING, "Auth", "AuthService", "Production", "Security", "Jane Smith", "Warning", System.currentTimeMillis(), System.currentTimeMillis()),
        Incident("INC-104", "UI minor bug", "Button alignment issue", Severity.LOW, IncidentStatus.RESOLVED, "UI", "WebConsole", "Staging", "Frontend", null, "Healthy", System.currentTimeMillis(), System.currentTimeMillis())
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Incidents") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(incidents) { incident ->
                IncidentCard(incident = incident, onClick = { onIncidentClick(incident.id) })
            }
        }
    }
}

@Composable
fun IncidentCard(incident: Incident, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = incident.id,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                SeverityChip(severity = incident.severity)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = incident.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "${incident.service} • ${incident.category}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusBadge(status = incident.status)
                Text(
                    text = "Assigned: ${incident.assignedEngineer ?: "Unassigned"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun SeverityChip(severity: Severity) {
    val color = when (severity) {
        Severity.CRITICAL -> Color.Red
        Severity.HIGH -> Color(0xFFFF9800)
        Severity.MEDIUM -> Color.Yellow
        Severity.LOW -> Color.Green
        Severity.INFO -> Color.Blue
    }
    
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = severity.name,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun StatusBadge(status: IncidentStatus) {
    Text(
        text = status.name,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.secondary,
        fontWeight = FontWeight.SemiBold
    )
}
