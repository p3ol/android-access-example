package com.example.viewbasedexample.model

import androidx.annotation.DrawableRes
import com.poool.access.compose.PaywallMode

data class Article(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val publication: Publication? = null,
    val metadata: Metadata,
    val paragraphs: List<Paragraph> = emptyList(),
    val isPremium: Boolean = false,
    val paywallType: PaywallMode = PaywallMode.HIDE,
    @DrawableRes val imageId: Int,
    @DrawableRes val imageThumbId: Int
)

data class Metadata(
    val date: String,
    val readTimeMinutes: Int
)

data class Publication(
    val name: String,
    val logoUrl: String
)

data class Paragraph(
    val type: ParagraphType,
    val text: String,
    val markups: List<Markup> = emptyList()
)

data class Markup(
    val type: MarkupType,
    val start: Int,
    val end: Int,
    val href: String? = null
)

enum class MarkupType {
    Link,
    Code,
    Italic,
    Bold,
}

enum class ParagraphType {
    Title,
    Caption,
    Header,
    Subhead,
    Text,
    CodeBlock,
    Quote,
    Bullet,
}
