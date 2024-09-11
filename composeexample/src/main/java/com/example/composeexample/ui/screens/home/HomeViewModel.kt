package com.example.composeexample.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.composeexample.R
import com.example.composeexample.data.articles.ArticlesRepository
import com.example.composeexample.data.Result
import com.example.composeexample.utils.ErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class HomeViewModel(
    private val articlesRepository: ArticlesRepository,
    preSelectedArticleId: String?
) : ViewModel() {

    private val viewModelState = MutableStateFlow(
        HomeViewModelState(
            isLoading = true,
            selectedArticleId = preSelectedArticleId,
            isArticleOpen = preSelectedArticleId != null
        )
    )

    val uiState = viewModelState
        .map(HomeViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        refreshArticles()
    }

    fun refreshArticles() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = articlesRepository.getArticlesFeed()
            viewModelState.update {
                when (result) {
                    is Result.Success -> it.copy(articlesFeed = result.data, isLoading = false)
                    is Result.Error -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_error
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }
            }
        }
    }

    fun selectArticle(articleId: String) {
        interactedWithArticleDetails(articleId)
    }

    fun errorShown(errorId: Long) {
        viewModelState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessages.filterNot { it.id == errorId }
            currentUiState.copy(errorMessages = errorMessages)
        }
    }

    fun interactedWithFeed() {
        viewModelState.update {
            it.copy(isArticleOpen = false)
        }
    }

    fun interactedWithArticleDetails(articleId: String) {
        viewModelState.update {
            it.copy(
                selectedArticleId = articleId,
                isArticleOpen = true
            )
        }
    }

    companion object {
        fun provideFactory(
            articlesRepository: ArticlesRepository,
            preSelectedArticleId: String? = null
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(articlesRepository, preSelectedArticleId) as T
            }
        }
    }
}
