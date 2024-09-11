package com.example.viewbasedexample.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewbasedexample.R
import com.example.viewbasedexample.data.articles.impl.FakeArticlesRepository
import com.example.viewbasedexample.data.Result
import com.example.viewbasedexample.utils.ErrorMessage
import com.poool.access.Access
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.Serializable
import java.util.UUID

private const val preSelectedArticleId = "articleId"

class HomeViewModel : ViewModel() {
    private val articlesRepository = FakeArticlesRepository()

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

    val accessSerializable: AccessSerializable = object : AccessSerializable {
        override val access: Access =
            Access("GP24fGU7rjdGCZ5bRvh9KahttH5fzxrPGKiSu1cabyTrwA8c3aYgI07oG6dQkTs5")
            .config(mapOf(
                "debug" to true,
                "cookies_enabled" to true,
            ))
    }

    init {
        println("HomeViewModel created")
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
}

interface AccessSerializable : Serializable {
    val access: Access
}
