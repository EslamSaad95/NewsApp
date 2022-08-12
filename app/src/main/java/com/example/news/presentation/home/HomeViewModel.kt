package com.example.news.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.entity.EgyptNewsEntity
import com.example.news.domain.usecase.TopHeadlinesUseCase
import com.example.news.domain.common.ApiFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: TopHeadlinesUseCase):ViewModel() {

    private val _egyptNewsLiveData by lazy { MutableLiveData<List<EgyptNewsEntity>>() }
    val egyptNewsLiveData get() = _egyptNewsLiveData

    private val _loadingLiveData by lazy { MutableLiveData<Boolean>() }
    val loadingLiveData get() = _loadingLiveData

    private val _noInternetConnectionLiveData by lazy { MutableLiveData<ApiFailure>() }
    val noInternetConnectionLiveData get() = _noInternetConnectionLiveData

    private val _errorLiveData by lazy { MutableLiveData<ApiFailure>() }
    val errorLiveData get() = _errorLiveData




    private fun getEgyptNews()
    {
        val queryMap by lazy { HashMap<String,String>() }
        queryMap["country"]="eg"

        _loadingLiveData.value=true
        viewModelScope.launch {
            _loadingLiveData.value=false
            val response =useCase.getEgyptNews(queryMap)

            response.value?.let { _egyptNewsLiveData.value=it }

            response.error?.let {
                when(it) {
                    is ApiFailure.ConnectionError -> _noInternetConnectionLiveData.value = it
                    else -> { _errorLiveData.value=it }
                }
            }
        }
    }
}