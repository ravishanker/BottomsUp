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

import android.arch.lifecycle.LiveData
import android.util.Log
import com.raywenderlich.android.bottomsup.data.api.BreweryDbApiService
import com.raywenderlich.android.bottomsup.data.database.BeerDao
import com.raywenderlich.android.bottomsup.model.Beer
import com.raywenderlich.android.bottomsup.model.Beers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor


class BreweryDbRepository(private val apiService: BreweryDbApiService,
                          private val beerDao: BeerDao,
                          private val executor: Executor) {

  private val apiKey = "YOUR_API_KEY"

  fun getBeers(page: Int): LiveData<List<Beer>> {
    fetchBeers(page)
    return beerDao.beers()
  }

  fun getBeer(beerId: String): Call<Beer> {
    return apiService.getBeer(beerId, apiKey)
  }

  fun fetchBeers(page: Int) {
    apiService.getBeers(page, apiKey).enqueue(beersCallback())
  }

  private fun beersCallback() = object : Callback<Beers> {
    override fun onFailure(call: Call<Beers>?, t: Throwable?) {
      Log.d("Failure Response: ", t.toString())
    }

    override fun onResponse(call: Call<Beers>?, response: Response<Beers>?) {
      response?.body()?.run {
        Log.d("Success Response: ", this.toString())
        executor.execute { beerDao.insert(this.data) }
      }
    }
  }
}