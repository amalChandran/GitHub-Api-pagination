package com.amal.closedpr.github.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.amal.closedpr.github.api.GithubAPI
import com.amal.closedpr.github.repository.GithubPRPagingSource
import com.amal.closedpr.github.repository.model.PRModelItem
import kotlinx.coroutines.flow.Flow

class PrViewModel(
    private val githubApi: GithubAPI
    ): ViewModel() {

    companion object {
        const val GITHUB_USER = "square"
        const val GITHUB_PROJECT = "retrofit"
    }

    val closedPRs: Flow<PagingData<PRModelItem>> = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 20),
        pagingSourceFactory = { GithubPRPagingSource(githubApi, GITHUB_PROJECT, GITHUB_USER) }
    ).flow.cachedIn(viewModelScope)
}