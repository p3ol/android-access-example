package com.example.composeexample.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composeexample.R
import com.example.composeexample.ui.components.FullScreenLoading
import com.example.composeexample.ui.screens.article.ArticleDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    val homeListLazyListState = rememberLazyListState()
    val articleBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    fun onRefreshArticles() = homeViewModel.refreshArticles()

    fun onSelectArticle(articleId: String) = homeViewModel.selectArticle(articleId)

    LoadingContent(
        empty = when (uiState) {
            is HomeUiState.HasArticles -> false
            is HomeUiState.NoArticles -> uiState.isLoading
        },
        loading = uiState.isLoading,
        onRefresh = { onRefreshArticles() }
    ) {
        when (uiState) {
            is HomeUiState.HasArticles -> {
                val uiState_ = uiState as HomeUiState.HasArticles

                ArticleList(
                    state = homeListLazyListState,
                    articlesFeed = uiState_.articlesFeed,
                    onArticleTapped = { onSelectArticle(it) }
                )

                if (uiState_.isArticleOpen) {
                    ArticleDetails(
                        article = uiState_.selectedArticle,
                        bottomSheetState = articleBottomSheetState,
                        onCloseArticle = { homeViewModel.interactedWithFeed() }
                    )
                }
            }
            is HomeUiState.NoArticles -> {
                if (uiState.errorMessages.isEmpty()) {
                    TextButton(
                        onClick = { onRefreshArticles() },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            stringResource(id = R.string.home_tap_to_load_content),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Box(
                        Modifier.fillMaxSize()
                    ) { }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit = { FullScreenLoading() },
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        PullToRefreshBox(
            isRefreshing = loading,
            onRefresh = onRefresh,
        ) {
            content()
        }
    }
}
