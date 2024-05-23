package com.example.composeexample.ui.screens.article

import androidx.compose.foundation.gestures.draggable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composeexample.model.Article

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ArticleDetails(
    article: Article,
    bottomSheetState: SheetState = rememberModalBottomSheetState(),
    onCloseArticle: () -> Unit,
) {
    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = { onCloseArticle() },
    ) {
        ArticleContent(
            article = article,
        )
    }
}
