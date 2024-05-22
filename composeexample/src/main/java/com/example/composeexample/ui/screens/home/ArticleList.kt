package com.example.composeexample.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.composeexample.R
import com.example.composeexample.model.Article
import com.example.composeexample.model.ArticlesFeed

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: LazyListState = rememberLazyListState(),
    articlesFeed: ArticlesFeed,
    onArticleTapped: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        state = state
    ) {
        item { ArticleListTopSection(articlesFeed.highlightedArticle, onArticleTapped) }
        if (articlesFeed.recommendedArticles.isNotEmpty()) {
            item {
                ArticleListSimpleSection(
                    articlesFeed.recommendedArticles,
                    onArticleTapped,
                )
            }
        }
        if (articlesFeed.popularArticles.isNotEmpty()) {
            item {
                ArticleListPopularSection(
                    articlesFeed.popularArticles, onArticleTapped
                )
            }
        }
    }
}

@Composable
private fun ArticleListSimpleSection(
    articles: List<Article>,
    navigateToArticle: (String) -> Unit,
) {
    Column {
        articles.forEach { article ->
            ArticleCardSimple(
                article = article,
                navigateToArticle = navigateToArticle,
            )
            ArticleListDivider()
        }
    }
}

@Composable
private fun ArticleListTopSection(article: Article, navigateToArticle: (String) -> Unit) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        text = stringResource(id = R.string.home_top_section_title),
        style = MaterialTheme.typography.titleMedium
    )
    ArticleCardTop(
        article = article,
        modifier = Modifier.clickable(onClick = { navigateToArticle(article.id) })
    )
    ArticleListDivider()
}

@Composable
private fun ArticleListPopularSection(
    articles: List<Article>,
    navigateToArticle: (String) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.home_popular_section_title),
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .height(IntrinsicSize.Max)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (article in articles) {
                ArticleCardPopular(
                    article,
                    navigateToArticle
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        ArticleListDivider()
    }
}

@Composable
private fun ArticleListDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}
