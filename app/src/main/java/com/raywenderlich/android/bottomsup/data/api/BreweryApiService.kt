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

package com.raywenderlich.android.bottomsup.data.api

import com.raywenderlich.android.bottomsup.model.Beer
import com.raywenderlich.android.bottomsup.model.Beers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * http://api.brewerydb.com.
 * @see "http://www.brewerydb.com/developers"
 * GET: /beers
 * GET: /beer/:beerId
 */

interface BreweryDbApiService {

  @GET("/v2/beers")
  fun getBeers(@Query("p") page: Int,
               @Query("key") apiKey: String,
               @Query("styleId") styleId: String = "15"): Call<Beers>

  @GET("/v2/beer/{beer_id}")
  fun getBeer(@Path("beer_id") beerId: String,
              @Query("key") apiKey: String): Call<Beer>
}
