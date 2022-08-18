package com.amanpathak.nasapictures.repo

import android.content.Context
import com.amanpathak.nasapictures.models.PhotoModel
import javax.inject.Inject


class MainRepository @Inject constructor(private val dataRepo : DataRepository) : Repository {

   override suspend fun fetchPhotoList(context: Context, model: Class<PhotoModel>): List<PhotoModel> {
    return dataRepo.fetchListForModel(context, model)
   }

}