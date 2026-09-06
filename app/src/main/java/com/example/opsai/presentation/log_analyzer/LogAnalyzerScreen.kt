package com.example.opsai.presentation.log_analyzer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogAnalyzerScreen() {
    var logs by remember { mutableStateOf("") }
    var analyzing by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("AI Log Analyzer") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "Paste your logs below for AI-powered root cause analysis.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            item {
                OutlinedTextField(
                    value = logs,
                    onValueChange = { logs = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    placeholder = { Text("Enter logs here...") }
                )
            }
            
            item {
                Button(
                    onClick = {
                        analyzing = true
                        // Simulate analysis
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = logs.isNotBlank() && !analyzing
                ) {
                    if (analyzing) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Analyze Logs")
                    }
                }
            }
            
            if (result != null || analyzing) {
                item {
                    AnalysisResultPlaceholder()
                }
            }
        }
    }
}

@Composable
fun AnalysisResultPlaceholder() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Analysis Results", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Detected: Connection Timeout Exception", style = MaterialTheme.typography.bodyMedium)
            Text("• Affected Service: payment-gateway", style = MaterialTheme.typography.bodyMedium)
            Text("• Possible Root Cause: Network firewall blocking port 5432", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Recommended: Check recent security group changes in AWS.", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
        }
    }
}
