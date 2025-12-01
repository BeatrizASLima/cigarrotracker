package com.example.cigarrotracker.data.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cigarrotracker.data.Noticia
import com.example.cigarrotracker.data.service.NewsApiService

class NoticiaPagingSource(
    private val newsApiService: NewsApiService
) : PagingSource<Int, Noticia>() {

    override fun getRefreshKey(state: PagingState<Int, Noticia>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Noticia> {
        return try {
            val page = params.key ?: 1
            val response = newsApiService.getNews(page = page)
            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
