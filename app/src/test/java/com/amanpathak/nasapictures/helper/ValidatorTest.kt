package com.amanpathak.nasapictures.helper

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class ValidatorTest {


    @Test
    fun `when url is valid`() {
        val url = "https://i.picsum.photos/id/1006/3000/2000.jpg?hmac=x83pQQ7LW1UTo8HxBcIWuRIVeN_uCg0cG6keXvNvM8g"
        val result = Validator.isValidUrl(url)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `when url is invalid`(){
        val url = "Hello World"
        val result = Validator.isValidUrl(url)
        assertThat(result).isEqualTo(false)
    }

}