package com.example.composeexample.data

import android.content.Context
import com.example.composeexample.data.articles.ArticlesRepository
import com.example.composeexample.data.articles.impl.FakeArticlesRepository

interface AppContainer {
    val articlesRepository: ArticlesRepository
}

class AppContainerImpl(private val applicationContext: Context) : AppContainer {

    override val articlesRepository: ArticlesRepository by lazy {
        FakeArticlesRepository()
    }
}
