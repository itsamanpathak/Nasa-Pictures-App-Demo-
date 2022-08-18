package com.amanpathak.nasapictures

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amanpathak.nasapictures.models.PhotoModel
import com.amanpathak.nasapictures.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(appContext : Application, repo : MainRepository) : AndroidViewModel(appContext) {
    val photoList = MutableLiveData<List<PhotoModel>>()

    init {

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                photoList.postValue(repo.fetchPhotoList(appContext, PhotoModel::class.java))
            }
        }
    }



}