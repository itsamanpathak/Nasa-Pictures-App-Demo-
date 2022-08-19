package com.amanpathak.nasapictures.repo

import android.content.Context
import com.amanpathak.nasapictures.models.PhotoModel

interface Repository {
    suspend fun fetchPhotoList(context: Context, model: Class<PhotoModel>): List<PhotoModel>
}