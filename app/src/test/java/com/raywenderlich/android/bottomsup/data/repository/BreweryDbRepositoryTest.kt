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

package com.raywenderlich.android.bottomsup.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.raywenderlich.android.bottomsup.data.api.BreweryDbApiService
import com.raywenderlich.android.bottomsup.data.database.BeerDao
import com.raywenderlich.android.bottomsup.model.Beer
import com.raywenderlich.android.bottomsup.model.Beers
import com.raywenderlich.android.bottomsup.util.InstantAppExecutor
import com.raywenderlich.android.bottomsup.util.LiveDataTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import retrofit2.Call
import org.mockito.Mockito.`when` as whenever


@RunWith(JUnit4::class)
class BreweryDbRepositoryTest {

    @Rule @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val service =  mock(BreweryDbApiService::class.java)
    private val beerDao = mock(BeerDao::class.java)
    private val executor = InstantAppExecutor()

    private val repository = BreweryDbRepository(service, beerDao, executor)


    @Test
    fun getBeers() {

        val beersCall = mock(Call::class.java) as Call<Beers>
        whenever(service.getBeers(1, "YOUR_API_KEY")).thenReturn(beersCall)

        val dbData: LiveData<List<Beer>> = MutableLiveData()
        whenever(beerDao.beers()).thenReturn(dbData)

        val beers: LiveData<List<Beer>> = repository.getBeers(1)
        val beersList =  LiveDataTestUtil.getValue(beers)

        verify(service).getBeers(1, "YOUR_API_KEY")
        verify(beerDao).beers()

        assertThat(beersList, `is`(not(emptyList())))
    }

}