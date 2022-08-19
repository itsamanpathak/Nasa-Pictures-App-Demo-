package com.amanpathak.nasapictures

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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
class MainViewModel @Inject constructor(appContext: Application, repo: MainRepository) :
    AndroidViewModel(appContext) {
    private val photoList = MutableLiveData<List<PhotoModel>>()
    var photoListLiveData: LiveData<List<PhotoModel>> = photoList

    private var currentPosition = MutableLiveData<Int>(0)
    var currentPositionLiveData: LiveData<Int> = currentPosition

    private var currentModel = MutableLiveData<PhotoModel?>()
    var currentModelLiveData: LiveData<PhotoModel?> = currentModel

    init {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                photoList.postValue(repo.fetchPhotoList(appContext, PhotoModel::class.java))
            }
        }
    }

    fun setScrollPosition(position: Int) {
        currentPosition.value = position

        currentModel.value = photoList.value?.get(position)
    }


}