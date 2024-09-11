package com.example.viewbasedexample.ui.home

import com.example.viewbasedexample.model.Article
import com.example.viewbasedexample.model.ArticlesFeed
import com.example.viewbasedexample.utils.ErrorMessage

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    data class NoArticles(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : HomeUiState

    data class HasArticles(
        val articlesFeed: ArticlesFeed,
        val selectedArticle: Article,
        val isArticleOpen: Boolean,
        val favorites: Set<String>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : HomeUiState
}

internal data class HomeViewModelState(
    val articlesFeed: ArticlesFeed? = null,
    val selectedArticleId: String? = null,
    val isArticleOpen: Boolean = false,
    val favorites: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val searchInput: String = "",
) {
    fun toUiState(): HomeUiState =
        if (articlesFeed == null) {
            HomeUiState.NoArticles(
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        } else {
            HomeUiState.HasArticles(
                articlesFeed = articlesFeed,
                selectedArticle = articlesFeed.allArticles.find {
                    it.id == selectedArticleId
                } ?: articlesFeed.highlightedArticle,
                isArticleOpen = isArticleOpen,
                favorites = favorites,
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        }
}
