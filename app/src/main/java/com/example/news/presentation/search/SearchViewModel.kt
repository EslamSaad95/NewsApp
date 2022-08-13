package com.example.news.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.common.ApiFailure
import com.example.news.domain.entity.TopHeadlinesEntity
import com.example.news.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: SearchUseCase) : ViewModel() {


    private val _searchResultsLiveData by lazy { MutableLiveData<List<TopHeadlinesEntity>>() }
    val searchResultsLiveData get() = _searchResultsLiveData

    private val _loadingLiveData by lazy { MutableLiveData<Boolean>() }
    val loadingLiveData get() = _loadingLiveData

    private val _errorLiveData by lazy { MutableLiveData<ApiFailure>() }
    val errorLiveData get() = _errorLiveData

    fun getSearchResults(keyWord: String) {
        val queryMap = HashMap<String, String>()
        queryMap["q"] = keyWord
        _loadingLiveData.value = true
        viewModelScope.launch {

            val response = useCase.getSearchResults(queryMap)

            response.value?.let {
                _loadingLiveData.value = false
                _searchResultsLiveData.value = it
            }

            _errorLiveData.value?.let {
                _loadingLiveData.value = false
                _errorLiveData.value = it
            }
        }
    }

}