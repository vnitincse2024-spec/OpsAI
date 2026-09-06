package com.example.opsai.presentation.create_incident

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.opsai.domain.model.AIAnalysis
import com.example.opsai.domain.model.Severity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateIncidentScreen(onIncidentCreated: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var severity by remember { mutableStateOf(Severity.MEDIUM) }
    var logs by remember { mutableStateOf("") }
    var aiSuggestion by remember { mutableStateOf<AIAnalysis?>(null) }
    var analyzing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Create Incident") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
            }
            item {
                OutlinedTextField(value = logs, onValueChange = { logs = it }, label = { Text("Logs (Optional)") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
            }
            
            item {
                Button(
                    onClick = {
                        analyzing = true
                        // Simulate AI Analysis
                        aiSuggestion = AIAnalysis(
                            rootCause = "Suspected pod OOMKill",
                            confidence = 92,
                            evidence = listOf("Memory usage reached limit", "Restart count increased"),
                            recommendedActions = listOf("Increase memory limits", "Check for memory leaks"),
                            suggestedSeverity = Severity.HIGH
                        )
                        analyzing = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    if (analyzing) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Analyze with AI")
                    }
                }
            }

            if (aiSuggestion != null) {
                item {
                    AISuggestionCard(aiSuggestion!!, onApprove = {
                        severity = it
                        aiSuggestion = null
                    })
                }
            }

            item {
                Button(
                    onClick = onIncidentCreated,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = title.isNotBlank()
                ) {
                    Text("Create Incident")
                }
            }
        }
    }
}

@Composable
fun AISuggestionCard(suggestion: AIAnalysis, onApprove: (Severity) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("AI Suggestions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Suggested Severity: ${suggestion.suggestedSeverity?.name ?: "N/A"}")
            Text("Root Cause: ${suggestion.rootCause}")
            
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { suggestion.suggestedSeverity?.let { onApprove(it) } }) {
                Text("Approve Suggestions")
            }
        }
    }
}
