package com.example.composeexample.model

data class ArticlesFeed(
    val highlightedArticle: Article,
    val recommendedArticles: List<Article>,
    val popularArticles: List<Article>,
    val recentArticles: List<Article>,
) {
    val allArticles: List<Article> =
        listOf(highlightedArticle) + recommendedArticles + popularArticles + recentArticles
}
