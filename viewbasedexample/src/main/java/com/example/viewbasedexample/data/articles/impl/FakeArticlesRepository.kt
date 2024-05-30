package com.example.viewbasedexample.data.articles.impl

import com.example.viewbasedexample.data.articles.ArticlesRepository
import com.example.viewbasedexample.model.Article
import com.example.viewbasedexample.model.ArticlesFeed
import com.example.viewbasedexample.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class FakeArticlesRepository : ArticlesRepository {

    private val articlesFeed = MutableStateFlow<ArticlesFeed?>(null)

    override suspend fun getArticle(articleId: String?): Result<Article> {
        return withContext(Dispatchers.IO) {
            val article = articles.allArticles.find { it.id == articleId }
            if (article == null) {
                Result.Error(IllegalArgumentException("Article not found"))
            } else {
                Result.Success(article)
            }
        }
    }

    override suspend fun getArticlesFeed(): Result<ArticlesFeed> {
        return withContext(Dispatchers.IO) {
            delay(800) // pretend we're on a slow network
            if (shouldRandomlyFail()) {
                Result.Error(IllegalStateException())
            } else {
                articlesFeed.update { articles }
                Result.Success(articles)
            }
        }
    }

    override fun observeArticlesFeed(): Flow<ArticlesFeed?> = articlesFeed

    private var requestCount = 0

    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0
}
