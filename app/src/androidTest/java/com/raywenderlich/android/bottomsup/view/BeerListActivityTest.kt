/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.bottomsup.view

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.raywenderlich.android.bottomsup.R
import com.raywenderlich.android.bottomsup.model.Beer
import kotlinx.android.synthetic.main.beer_list.view.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * BeerListActivityTest.
 * Due to bug in Android Studio with Kotlin test files.  For now, Run all the tests in the package
 * by Right clicking on androidTest package and choosing Run 'Tests in 'package
 */

@LargeTest
@RunWith(AndroidJUnit4::class)
class BeerListActivityTest {

  @Rule
  @JvmField
  val activityTestRule = ActivityTestRule<BeerListActivity>(BeerListActivity::class.java)

  @Test
  fun useAppContext() {
    // Context of the app under test.
    val appContext = InstrumentationRegistry.getTargetContext()
    Assert.assertEquals("com.raywenderlich.android.bottomsup", appContext.packageName)
  }

  @Test
  @Throws(Exception::class)
  fun testAppName() {
    onView(withText("Bottoms Up")).check(matches(isDisplayed()))
  }

  @Test
  @Throws(Exception::class)
  fun testBeerListDisplayed() {
    onView(withId(R.id.beer_list)).check(matches(isDisplayed()))
    onView(withId(R.id.beer_list)).perform(RecyclerViewActions
        .actionOnItemAtPosition<BeersRecyclerViewAdapter.ViewHolder>(1, click()))
  }

}