package com.example.opsai.presentation.incident_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.opsai.domain.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentDetailScreen(incidentId: String) {
    // Mock data
    val incident = Incident(
        id = incidentId,
        title = "Database connection pool exhaustion",
        description = "Service 'payment-service' is experiencing high latency and connection timeouts to the main PostgreSQL instance.",
        severity = Severity.CRITICAL,
        status = IncidentStatus.INVESTIGATING,
        category = "Database",
        service = "PaymentService",
        environment = "Production",
        assignedTeam = "Backend",
        assignedEngineer = "John Doe",
        slaStatus = "Breached",
        createdAt = System.currentTimeMillis() - 7200000,
        updatedAt = System.currentTimeMillis(),
        aiAnalysis = AIAnalysis(
            rootCause = "Database connection pool exhaustion",
            confidence = 87,
            evidence = listOf("Connection timeout errors", "High connection utilization", "Similar previous incidents"),
            recommendedActions = listOf("Check database connections", "Inspect recent deployment", "Review pool configuration")
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(incident.id) })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                IncidentHeader(incident)
            }
            
            item {
                AIAnalysisCard(incident.aiAnalysis)
            }
            
            item {
                IncidentInfoSection(incident)
            }
            
            item {
                ActionButtons()
            }
        }
    }
}

@Composable
fun IncidentHeader(incident: Incident) {
    Column {
        Text(text = incident.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SuggestionChip(onClick = {}, label = { Text(incident.severity.name) })
            SuggestionChip(onClick = {}, label = { Text(incident.status.name) })
        }
    }
}

@Composable
fun AIAnalysisCard(analysis: AIAnalysis?) {
    if (analysis == null) return
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("AI Analysis", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.weight(1f))
                Text("${analysis.confidence}% Confidence", style = MaterialTheme.typography.labelMedium)
            }
            
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.2f))
            
            Text("ROOT CAUSE", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Text(analysis.rootCause, style = MaterialTheme.typography.bodyLarge)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text("EVIDENCE", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            analysis.evidence.forEach { 
                Text("• $it", style = MaterialTheme.typography.bodyMedium)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text("RECOMMENDED ACTIONS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            analysis.recommendedActions.forEachIndexed { index, action ->
                Text("${index + 1}. $action", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun IncidentInfoSection(incident: Incident) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Information", style = MaterialTheme.typography.titleMedium)
        InfoRow("Service", incident.service)
        InfoRow("Environment", incident.environment)
        InfoRow("Assigned Team", incident.assignedTeam)
        InfoRow("SLA Status", incident.slaStatus)
        
        Spacer(modifier = Modifier.height(8.dp))
        Text("Description", style = MaterialTheme.typography.titleSmall)
        Text(incident.description, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun ActionButtons() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Start Investigation")
        }
        OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Mark Mitigated")
        }
        Button(
            onClick = {}, 
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Resolve Incident")
        }
    }
}
