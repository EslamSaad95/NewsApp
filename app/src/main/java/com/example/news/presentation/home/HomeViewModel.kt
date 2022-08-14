package com.example.news.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.common.ApiFailure
import com.example.news.domain.entity.TopHeadlinesEntity
import com.example.news.domain.usecase.NewsDataBaseUseCase
import com.example.news.domain.usecase.TopHeadlinesUseCase
import com.example.news.presentation.utils.localEntity.FavCheckDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: TopHeadlinesUseCase,
    private val dbUseCase: NewsDataBaseUseCase
) : ViewModel() {

    private val _egyptNewsLiveData by lazy { MutableLiveData<List<TopHeadlinesEntity>>() }
    val egyptNewsLiveData get() = _egyptNewsLiveData

    private val _latestNewsLiveData by lazy { MutableLiveData<List<TopHeadlinesEntity>>() }
    val latestNewsEntity get() = _latestNewsLiveData

    private val _loadingLiveData by lazy { MutableLiveData<Boolean>() }
    val loadingLiveData get() = _loadingLiveData

    private val _errorLiveData by lazy { MutableLiveData<ApiFailure>() }
    val errorLiveData get() = _errorLiveData

    private val _snackBarLiveData by lazy { MutableLiveData<String>() }
    val snackBarLiveData get() = _snackBarLiveData

    private val _favItemUpdateLiveData by lazy { MutableLiveData<FavCheckDataClass>() }
    val favItemUpdateLiveData get() = _favItemUpdateLiveData

    init {
        getEgyptNews()
        getLatestNews()
    }


    private fun getEgyptNews() {
        val queryMap by lazy { HashMap<String, String>() }
        queryMap["country"] = "eg"

        _loadingLiveData.value = true
        viewModelScope.launch {

            useCase.getEgyptNews(queryMap).collect { response ->

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
    }


    private fun getLatestNews() {
        val queryMap by lazy { HashMap<String, String>() }
        queryMap["sources"] = "bbc-news,the-next-web"

        _loadingLiveData.value = true
        viewModelScope.launch {
            _loadingLiveData.value = false

            useCase.getLatestNews(queryMap).collect { response ->
                response.value?.let { _latestNewsLiveData.value = it }

                response.error?.let {
                    _errorLiveData.value = it
                }
            }
        }
    }


    fun addToDatabase(entity: TopHeadlinesEntity, position: Int) {
        viewModelScope.launch {
            try {
                dbUseCase.addTeam(entity)
                _favItemUpdateLiveData.value = FavCheckDataClass(entity.title, true, position)
            } catch (e: Exception) {
                _favItemUpdateLiveData.value = FavCheckDataClass(entity.title, false, position)
                _snackBarLiveData.value = e.message.toString()
            }
        }
    }

    fun removeFromDatabase(entity: TopHeadlinesEntity, position: Int) {
        viewModelScope.launch {
            try {
                _favItemUpdateLiveData.value = FavCheckDataClass(entity.title, false, position)
                dbUseCase.removeFromTeams(entity)
            } catch (e: Exception) {
                _favItemUpdateLiveData.value = FavCheckDataClass(entity.title, true, position)
                _snackBarLiveData.value = e.message.toString()
            }
        }
    }
}