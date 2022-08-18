package com.amanpathak.nasapictures.repo

import androidx.test.platform.app.InstrumentationRegistry
import com.amanpathak.nasapictures.models.PhotoModel
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class DataRepositoryTest {
    lateinit var dataRepo : DataRepository

    @Before
    fun initialize(){
        dataRepo = DataRepository()
    }


    @Test
    fun fetchListForModel() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val list = dataRepo.fetchListForModel(context,PhotoModel::class.java)
        assertThat(list).isNotEmpty()
    }
}