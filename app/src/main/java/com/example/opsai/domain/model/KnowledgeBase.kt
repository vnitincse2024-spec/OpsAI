package com.example.opsai.domain.model

data class KnowledgeBaseArticle(
    val id: String,
    val title: String,
    val problem: String,
    val symptoms: String,
    val rootCause: String,
    val resolution: String,
    val prevention: String,
    val tags: List<String>
)
