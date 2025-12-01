package com.example.cigarrotracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cigarrotracker.data.Noticia
import com.example.cigarrotracker.data.repository.NoticiaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NoticiaViewModel @Inject constructor(
    private val repository: NoticiaRepository
) : ViewModel() {

    fun getNoticias(): Flow<PagingData<Noticia>> =
        repository.getNoticias().cachedIn(viewModelScope)
}
