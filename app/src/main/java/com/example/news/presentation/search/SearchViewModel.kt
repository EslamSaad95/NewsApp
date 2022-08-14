package com.example.news.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.common.ApiFailure
import com.example.news.domain.entity.TopHeadlinesEntity
import com.example.news.domain.usecase.NewsDataBaseUseCase
import com.example.news.domain.usecase.SearchUseCase
import com.example.news.presentation.utils.localEntity.FavCheckDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchUseCase,
    private val dbUseCase: NewsDataBaseUseCase
) : ViewModel() {


    private val _searchResultsLiveData by lazy { MutableLiveData<List<TopHeadlinesEntity>>() }
    val searchResultsLiveData get() = _searchResultsLiveData

    private val _loadingLiveData by lazy { MutableLiveData<Boolean>() }
    val loadingLiveData get() = _loadingLiveData

    private val _errorLiveData by lazy { MutableLiveData<ApiFailure>() }
    val errorLiveData get() = _errorLiveData

    private val _snackBarLiveData by lazy { MutableLiveData<String>() }
    val snackBarLiveData get() = _snackBarLiveData

    private val _favItemUpdateLiveData by lazy { MutableLiveData<FavCheckDataClass>() }
    val favItemUpdateLiveData get() = _favItemUpdateLiveData

    fun getSearchResults(keyWord: String) {
        val queryMap = HashMap<String, String>()
        queryMap["q"] = keyWord
        _loadingLiveData.value = true
        viewModelScope.launch {

            useCase.getSearchResults(queryMap).collect { response ->
                response.value?.let {
                    _loadingLiveData.value = false
                    _searchResultsLiveData.value = it
                }


                response.error?.let {
                    _loadingLiveData.value = false
                    _errorLiveData.value = it
                }
            }

        }
    }


    fun addToDatabase(entity: TopHeadlinesEntity, position: Int) {
        viewModelScope.launch {
            try {
                dbUseCase.addTeam(entity)
                _favItemUpdateLiveData.value = FavCheckDataClass(true, position)
            } catch (e: Exception) {
                _favItemUpdateLiveData.value = FavCheckDataClass(false, position)
                _snackBarLiveData.value = e.message.toString()
            }
        }
    }

    fun removeFromDatabase(entity: TopHeadlinesEntity, position: Int) {
        viewModelScope.launch {
            try {
                _favItemUpdateLiveData.value = FavCheckDataClass(false, position)
                dbUseCase.removeFromTeams(entity)
            } catch (e: Exception) {
                _favItemUpdateLiveData.value = FavCheckDataClass(true, position)
                _snackBarLiveData.value = e.message.toString()
            }
        }
    }


}