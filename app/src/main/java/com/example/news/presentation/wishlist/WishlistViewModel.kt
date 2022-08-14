package com.example.news.presentation.wishlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.entity.TopHeadlinesEntity
import com.example.news.domain.usecase.NewsDataBaseUseCase
import com.example.news.presentation.utils.localEntity.FavCheckDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(private val dbUseCase: NewsDataBaseUseCase) :
    ViewModel() {

    private val _favNewsLiveData by lazy { MutableLiveData<List<TopHeadlinesEntity>>() }
    val favNewsLiveData get() = _favNewsLiveData

    private val _loadingLiveData by lazy { MutableLiveData<Boolean>() }
    val loadingLiveData get() = _loadingLiveData


    private val _snackBarLiveData by lazy { MutableLiveData<String>() }
    val snackBarLiveData get() = _snackBarLiveData

    private val _favItemUpdateLiveData by lazy { MutableLiveData<FavCheckDataClass>() }
    val favItemUpdateLiveData get() = _favItemUpdateLiveData


    private fun getFavNews() {
        _loadingLiveData.value = true
        viewModelScope.launch {


            try {
                _loadingLiveData.value = false
                dbUseCase.getAllTeams().collect { _favNewsLiveData.value = it }
            } catch (e: Exception) {
                _loadingLiveData.value = false
                _snackBarLiveData.value = e.message.toString()
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