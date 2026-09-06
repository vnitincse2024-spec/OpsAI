package com.example.opsai.presentation.knowledge_base

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KnowledgeBaseScreen() {
    var searchQuery by remember { mutableStateOf("") }
    
    val articles = listOf(
        "Fixing Kafka Lag",
        "Redis Memory Management",
        "PostgreSQL Vacuum Guide",
        "Nginx 502 Troubleshooting"
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Knowledge Base") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search articles...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(articles.filter { it.contains(searchQuery, ignoreCase = true) }) { article ->
                    ArticleItem(title = article)
                }
            }
        }
    }
}

@Composable
fun ArticleItem(title: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Last updated by Admin • 3 days ago",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
