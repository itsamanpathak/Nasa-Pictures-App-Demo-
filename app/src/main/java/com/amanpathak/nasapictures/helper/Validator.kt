package com.amanpathak.nasapictures.helper

import android.util.Patterns
import android.webkit.URLUtil
import android.webkit.URLUtil.isValidUrl
import java.net.MalformedURLException
import java.net.URISyntaxException
import java.net.URL

object Validator {

    fun isValidUrl(imageUrl: String): Boolean {

        try {
            val url = URL(imageUrl)
            url.toURI()
        } catch (e: MalformedURLException) {
            return false
        } catch (e: URISyntaxException) {
            return false
        }

        return true
    }


}