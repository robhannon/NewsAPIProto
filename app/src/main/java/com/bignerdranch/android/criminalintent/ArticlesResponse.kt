package com.bignerdranch.android.criminalintent

import com.squareup.moshi.Json

data class ArticlesResponse (
    val status: String,
    @Json(name = "totalResults") val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    @Json(name = "urlToImage") val urlToImage: String?,
    @Json(name = "publishedAt") val publishedAt: String,
    val content: String?
)

data class Source(
    val id: String?,
    val name: String
)