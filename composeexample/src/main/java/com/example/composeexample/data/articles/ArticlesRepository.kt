package com.example.composeexample.data.articles

import com.example.composeexample.model.Article
import com.example.composeexample.model.ArticlesFeed
import com.example.composeexample.data.Result
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {

    suspend fun getArticle(articleId: String?): Result<Article>

    suspend fun getArticlesFeed(): Result<ArticlesFeed>

    fun observeArticlesFeed(): Flow<ArticlesFeed?>
}
