package com.amanpathak.nasapictures.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.amanpathak.nasapictures.models.PhotoModel
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Inject

class DataRepository @Inject constructor() {
    private val datafile = "data.json"

    fun <T> fetchListForModel(context: Context, t: Class<T>): List<T> {
        return getArrayListFromJson(loadJSONFromAsset(context)!!, t)
    }


    private fun loadJSONFromAsset(context: Context): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = context.assets.open(datafile)
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }


    private fun <T> getArrayListFromJson(jsonArrayString: String, m: Class<T>): List<T> {

        val list = ArrayList<T>()
        val gson = Gson()

        try {
            val jsonArray = JSONArray(jsonArrayString)
            for (i in 0 until jsonArray.length()) {

                val model: T = gson.fromJson(jsonArray.getString(i), m)
                list.add(model)
            }
        } catch (e: JSONException) {

        }

        return list

    }


}