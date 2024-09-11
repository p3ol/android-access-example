package com.example.viewbasedexample.data.articles

import com.example.viewbasedexample.data.Result
import com.example.viewbasedexample.model.Article
import com.example.viewbasedexample.model.ArticlesFeed
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {

    suspend fun getArticle(articleId: String?): Result<Article>

    suspend fun getArticlesFeed(): Result<ArticlesFeed>

    fun observeArticlesFeed(): Flow<ArticlesFeed?>
}
