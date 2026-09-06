package com.example.opsai.data.remote.dto

import com.example.opsai.domain.model.KnowledgeBaseArticle
import com.google.gson.annotations.SerializedName

data class KnowledgeBaseArticleDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("problem") val problem: String,
    @SerializedName("symptoms") val symptoms: String,
    @SerializedName("root_cause") val root_cause: String,
    @SerializedName("resolution") val resolution: String,
    @SerializedName("prevention") val prevention: String,
    @SerializedName("tags") val tags: List<String>
) {
    fun toKnowledgeBaseArticle(): KnowledgeBaseArticle {
        return KnowledgeBaseArticle(
            id = id,
            title = title,
            problem = problem,
            symptoms = symptoms,
            rootCause = root_cause,
            resolution = resolution,
            prevention = prevention,
            tags = tags
        )
    }
}
