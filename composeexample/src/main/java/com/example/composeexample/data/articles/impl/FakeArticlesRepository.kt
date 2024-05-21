package com.example.composeexample.data.articles.impl

import com.example.composeexample.data.articles.ArticlesRepository
import com.example.composeexample.model.Article
import com.example.composeexample.model.ArticlesFeed
import com.example.composeexample.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class FakeArticlesRepository : ArticlesRepository {

    private val postsFeed = MutableStateFlow<ArticlesFeed?>(null)

    override suspend fun getArticle(postId: String?): Result<Article> {
        return withContext(Dispatchers.IO) {
            val post = posts.allArticles.find { it.id == postId }
            if (post == null) {
                Result.Error(IllegalArgumentException("Article not found"))
            } else {
                Result.Success(post)
            }
        }
    }

    override suspend fun getArticlesFeed(): Result<ArticlesFeed> {
        return withContext(Dispatchers.IO) {
            delay(800) // pretend we're on a slow network
            if (shouldRandomlyFail()) {
                Result.Error(IllegalStateException())
            } else {
                postsFeed.update { posts }
                Result.Success(posts)
            }
        }
    }

    override fun observeArticlesFeed(): Flow<ArticlesFeed?> = postsFeed

    private var requestCount = 0

    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0
}
