package com.example.composeexample.ui.screens.home

import com.example.composeexample.model.Article
import com.example.composeexample.model.ArticlesFeed
import com.example.composeexample.utils.ErrorMessage

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    data class NoArticles(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : HomeUiState

    data class HasArticles(
        val postsFeed: ArticlesFeed,
        val selectedPost: Article,
        val isArticleOpen: Boolean,
        val favorites: Set<String>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : HomeUiState
}

internal data class HomeViewModelState(
    val postsFeed: ArticlesFeed? = null,
    val selectedPostId: String? = null,
    val isArticleOpen: Boolean = false,
    val favorites: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val searchInput: String = "",
) {
    fun toUiState(): HomeUiState =
        if (postsFeed == null) {
            HomeUiState.NoArticles(
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        } else {
            HomeUiState.HasArticles(
                postsFeed = postsFeed,
                selectedPost = postsFeed.allArticles.find {
                    it.id == selectedPostId
                } ?: postsFeed.highlightedArticle,
                isArticleOpen = isArticleOpen,
                favorites = favorites,
                isLoading = isLoading,
                errorMessages = errorMessages,
            )
        }
}
