package com.example.opsai.domain.repository

import com.example.opsai.domain.model.KnowledgeBaseArticle
import com.example.opsai.utils.Resource
import kotlinx.coroutines.flow.Flow

interface KnowledgeBaseRepository {
    fun getArticles(query: String? = null): Flow<Resource<List<KnowledgeBaseArticle>>>
    fun getArticleById(id: String): Flow<Resource<KnowledgeBaseArticle>>
}
