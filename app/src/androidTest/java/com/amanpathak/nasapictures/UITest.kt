package com.amanpathak.nasapictures

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.amanpathak.nasapictures.adapters.GalleryAdapter
import com.amanpathak.nasapictures.models.PhotoModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class UITest{
    lateinit var photoModel : PhotoModel
    var listPos = 9


    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initialize(){
        photoModel = PhotoModel(title = "Decorating the Sky",
            thumbnail = "https://apod.nasa.gov/apod/image/1912/m78ldn1622barnardsloopJulio1100.jpg",
            copyright = "Leonardo Julio",
            date = "2019-12-12",
            url = "https://apod.nasa.gov/apod/image/1912/m78ldn1622barnardsloopJulio.jpg",
            mediaType = "image",
            serviceVersion = "v1",
            explanation = "Bright stars, clouds of dust and glowing nebulae decorate this cosmic scene, a skyscape just north of Orion's belt. Close to the plane of our Milky Way galaxy, the wide field view spans about 5.5 degrees. Striking bluish M78, a reflection nebula, is on the right. M78's tint is due to dust preferentially reflecting the blue light of hot, young stars. In colorful contrast, the red sash of glowing hydrogen gas sweeping through the center is part of the region's faint but extensive emission nebula known as Barnard's Loop. At lower left, a dark dust cloud forms a prominent silhouette cataloged as LDN 1622. While M78 and the complex Barnard's Loop are some 1,500 light-years away, LDN 1622 is likely to be much closer, only about 500 light-years distant from our fair planet Earth."
        )
    }



    /*Is Photo RecyclerView is Visible*/
    @Test
    fun isImageGridFragmentVisibleOnStart(){
        onView(withId(R.id.photoRecyclerView)).check(matches(isDisplayed()))
    }


    /*Select Photo and Nav to ImageDetailFragment*/
    @Test
    fun onPhotoSelectNavToImageDetail(){
        onView(withId(R.id.photoRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<GalleryAdapter.ViewHolder>(listPos,click()))
        onView(withId(R.id.title)).check(matches(withText(photoModel.title)))
    }

    /*From ImageGridFragment to ImageMetaFragment*/
    @Test
    fun does_ImageMetaFragment_IsVisible_OnOptionMenu_Details_IsClicked_In_ImageDetailFragment(){
        onView(withId(R.id.photoRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<GalleryAdapter.ViewHolder>(listPos,click()))
        onView(withId(R.id.menu)).perform(click())
        onView(withText(R.string.details)).perform(click())
        onView(withId(R.id.metaTitle)).check(matches(withText("Name : ${photoModel.title}")))

    }

    /*After Going to ImageDetailFragment
    it comes back to ImageGridFragment with backPress*/
    @Test
    fun doesImageGridFragmentIsVisibleAfterBackNavigationFromDetail(){
        onView(withId(R.id.photoRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<GalleryAdapter.ViewHolder>(listPos,click()))
        onView(withId(R.id.back)).perform(click())
        onView(withId(R.id.photoRecyclerView)).check(matches(isDisplayed()))
    }


}