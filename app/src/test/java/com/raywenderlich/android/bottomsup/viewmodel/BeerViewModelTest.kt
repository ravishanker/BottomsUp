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

package com.raywenderlich.android.bottomsup.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.raywenderlich.android.bottomsup.data.repository.BreweryDbRepository
import com.raywenderlich.android.bottomsup.model.Beer
import com.raywenderlich.android.bottomsup.util.LiveDataTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.mockito.Mockito.`when` as whenever


@RunWith(JUnit4::class)
class BeerViewModelTest {

  @Rule
  @JvmField
  val instantExecutorRule = InstantTaskExecutorRule()

  private val repository by lazy { mock(BreweryDbRepository::class.java) }
  private lateinit var beerViewModel: BeerViewModel
  private val beersList: LiveData<List<Beer>> = MutableLiveData()

  @Before
  fun setUp() {
    whenever(repository.getBeers(1)).thenReturn(beersList)
    beerViewModel = BeerViewModel(repository)
  }

  @Test
  fun testNull() {
    assertThat(beerViewModel.getBeers(), notNullValue())
  }

  @Test
  fun testCallRepo() {
    val captor = ArgumentCaptor.forClass(Int::class.java)

    verify(repository).getBeers(captor.capture())
    assertThat(captor.value, `is`(1))
  }

  @Test
  fun sendResultToUi() {
    val observer = mock(Observer::class.java) as Observer<List<Beer>>
    val liveData = beerViewModel.getBeers() //.observeForever(observer)
    beersList.observeForever(observer)

    assertThat(liveData, `is`(beersList))
    assertThat(LiveDataTestUtil.getValue(liveData), `is`(LiveDataTestUtil.getValue(beersList)))
    verifyNoMoreInteractions(observer)
  }

}