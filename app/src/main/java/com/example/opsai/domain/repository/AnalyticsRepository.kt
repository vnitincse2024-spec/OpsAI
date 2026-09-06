package com.example.opsai.domain.repository

import com.example.opsai.domain.model.Analytics
import com.example.opsai.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AnalyticsRepository {
    fun getAnalytics(): Flow<Resource<Analytics>>
}
