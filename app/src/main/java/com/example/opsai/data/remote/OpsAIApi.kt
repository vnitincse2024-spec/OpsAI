package com.example.opsai.data.remote

import com.example.opsai.data.remote.dto.*
import retrofit2.http.*

interface OpsAIApi {

    @POST("/api/auth/login")
    suspend fun login(@Body request: Map<String, String>): AuthTokenDto

    @GET("/api/incidents")
    suspend fun getIncidents(): List<IncidentDto>

    @POST("/api/incidents")
    suspend fun createIncident(@Body incident: IncidentDto): IncidentDto

    @GET("/api/incidents/{id}")
    suspend fun getIncidentById(@Path("id") id: String): IncidentDto

    @PUT("/api/incidents/{id}")
    suspend fun updateIncident(@Path("id") id: String, @Body body: Map<String, String>): IncidentDto

    @POST("/api/incidents/{id}/analyze")
    suspend fun analyzeIncident(@Path("id") id: String): AIAnalysisDto

    @POST("/api/logs/analyze")
    suspend fun analyzeLogs(@Body body: Map<String, String>): AIAnalysisDto

    @GET("/api/incidents/{id}/similar")
    suspend fun getSimilarIncidents(@Path("id") id: String): List<IncidentDto>

    @GET("/api/analytics")
    suspend fun getAnalytics(): AnalyticsDto

    @GET("/api/knowledge-base")
    suspend fun getKnowledgeBase(@Query("q") query: String? = null): List<KnowledgeBaseArticleDto>
}
