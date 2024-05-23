package com.example.composeexample.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.composeexample.R
import com.example.composeexample.model.Article

@Composable
fun DateAndReadTime(
    article: Article,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Text(
            text = stringResource(
                id = R.string.home_article_min_read,
                formatArgs = arrayOf(
                    article.metadata.date,
                    article.metadata.readTimeMinutes
                )
            ),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ArticleImage(article: Article, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(article.imageThumbId),
        contentDescription = null,
        modifier = modifier
            .size(40.dp, 40.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun ArticleTitle(article: Article) {
    Text(
        text = article.title,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun ArticleCardSimple(
    article: Article,
    navigateToArticle: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(onClick = { navigateToArticle(article.id) })
    ) {
        ArticleImage(article, Modifier.padding(16.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp)
        ) {
            ArticleTitle(article)
            DateAndReadTime(article)
        }
    }
}
