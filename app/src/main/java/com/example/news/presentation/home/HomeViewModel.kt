package com.example.news.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.common.ApiFailure
import com.example.news.domain.entity.TopHeadlinesEntity
import com.example.news.domain.usecase.TopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: TopHeadlinesUseCase) : ViewModel() {

    private val _egyptNewsLiveData by lazy { MutableLiveData<List<TopHeadlinesEntity>>() }
    val egyptNewsLiveData get() = _egyptNewsLiveData

    private val _latestNewsLiveData by lazy { MutableLiveData<List<TopHeadlinesEntity>>() }
    val latestNewsEntity get() = _latestNewsLiveData

    private val _loadingLiveData by lazy { MutableLiveData<Boolean>() }
    val loadingLiveData get() = _loadingLiveData

    private val _errorLiveData by lazy { MutableLiveData<ApiFailure>() }
    val errorLiveData get() = _errorLiveData

    init {
        getEgyptNews()
        getLatestNews()
    }


    private fun getEgyptNews() {
        val queryMap by lazy { HashMap<String, String>() }
        queryMap["country"] = "eg"

        _loadingLiveData.value = true
        viewModelScope.launch {

            val response = useCase.getEgyptNews(queryMap)

            response.value?.let {
                _loadingLiveData.value = false
                _egyptNewsLiveData.value = it
            }

            response.error?.let {
                _loadingLiveData.value = false
                _errorLiveData.value = it
            }
        }
    }


    private fun getLatestNews() {
        val queryMap by lazy { HashMap<String, String>() }
        queryMap["sources"] = "bbc-news,the-next-web"

        _loadingLiveData.value = true
        viewModelScope.launch {
            _loadingLiveData.value = false
            val response = useCase.getLatestNews(queryMap)

            response.value?.let { _latestNewsLiveData.value = it }

            response.error?.let {
                _errorLiveData.value = it
            }
        }
    }
}