package com.example.cigarrotracker.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.cigarrotracker.data.pager.NoticiaPagingSource
import com.example.cigarrotracker.data.service.NewsApiService
import javax.inject.Inject

class NoticiaRepository @Inject constructor(
    private val newsApiService: NewsApiService
) {
    fun getNoticias() = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { NoticiaPagingSource(newsApiService) }
    ).flow
}
