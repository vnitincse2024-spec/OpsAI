package com.example.opsai.presentation.analytics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AnalyticsScreen() {
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("Analytics")
        }
    }
}
