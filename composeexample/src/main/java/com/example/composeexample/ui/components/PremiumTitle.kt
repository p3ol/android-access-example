package com.example.composeexample.ui.components

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.composeexample.model.Article

@Composable
fun PremiumTitle(
    modifier: Modifier = Modifier,
    article: Article,
    style: TextStyle = typography.titleLarge,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    width: Int = 24,
    height: Int = 24
) {
    val iconId = "inlineContent"
    val title = buildAnnotatedString {
        if (article.isPremium) appendInlineContent(iconId, "[icon]")
        append(article.title)
    }
    val inlineTitle = mapOf(
        Pair(
            iconId,
            InlineTextContent(
                Placeholder(
                    width = width.sp,
                    height = height.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Lock icon",
                    tint = Color(0xFFEAB306)
                )
            }
        )
    )

    Text(
        text = title,
        style = style,
        modifier = modifier,
        inlineContent = inlineTitle,
        overflow = overflow,
        maxLines = maxLines
    )
}
