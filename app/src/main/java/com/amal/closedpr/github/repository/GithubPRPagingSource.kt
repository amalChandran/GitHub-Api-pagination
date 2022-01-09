package com.amal.closedpr.github.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.PagingSource.LoadResult.Page
import com.amal.closedpr.github.api.GithubAPI
import com.amal.closedpr.github.repository.model.PRModelItem
import retrofit2.HttpException
import java.io.IOException
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GithubPRPagingSource(
    private val githubApi: GithubAPI,
    private val repoName: String,
    private val userId: String): PagingSource<Int, PRModelItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PRModelItem> {
        return try {
            val pageNumber = params.key ?: 1
            val loadSize = params.loadSize ?: 10
            val data = githubApi.getClosedPRs(
                userId = userId,
                repoName = repoName,
                page = pageNumber,
                perPage = loadSize
            )

            data.map {
                it.closedAt = formatTime(it.closedAt)
                it.createdAt = formatTime(it.createdAt)
            }

            val nextPageNumber: Int? = if (data.size < loadSize) {
                null
            } else {
                pageNumber + 1
            }

            Page(
                data = data,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    private fun formatTime(time: String): String {
        return try {
            val sdf: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val date = sdf.parse(time)
            DateFormat.getDateInstance().format(date!!)
        } catch (e: ParseException) {
            time
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PRModelItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}