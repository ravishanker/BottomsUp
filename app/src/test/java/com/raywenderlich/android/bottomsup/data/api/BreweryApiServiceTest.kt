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

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.raywenderlich.android.bottomsup.model.Beers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*


/**
 * BreweryDbApiTest.
 */

@RunWith(JUnit4::class)
class BreweryApiServiceTest {

  @Rule
  @JvmField
  val instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var mockWebServer: MockWebServer

  private val apiKey = "YOUR_API_KEY"

  private lateinit var service: BreweryDbApiService

  @Before
  @Throws(IOException::class)
  fun createService() {
    mockWebServer = MockWebServer()

    service = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create(BreweryDbApiService::class.java)
  }

  @After
  @Throws(IOException::class)
  fun stopService() {
    mockWebServer.shutdown()
  }

  @Test
  @Throws(IOException::class, InterruptedException::class)
  fun getBeers() {
    enqueueResponse("get_v2_beers.json")

    val call: Call<Beers> = service.getBeers(1, apiKey)
    val response: Response<Beers> = call.execute()
    val beers = response.body().run { this?.data }

    val request = mockWebServer.takeRequest()

    assertThat(request.path, `is`("/v2/beers?p=1&key=YOUR_API_KEY&styleId=15"))
    assertTrue(response.isSuccessful)
    assertThat(beers, notNullValue())
    assertThat(beers?.size, `is`(2))
    assertThat(beers?.first()?.id, `is`("HXKxpc"))
    assertThat(beers?.last()?.id, `is`("HXKpcx"))
  }


  @Throws(IOException::class)
  private fun enqueueResponse(fileName: String) {
    enqueueResponse(fileName, Collections.emptyMap())
  }

  @Throws(IOException::class)
  private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
    val inputStream = javaClass.classLoader.getResourceAsStream(fileName)
    val source = Okio.buffer(Okio.source(inputStream))
    val mockResponse = MockResponse()
    for ((key, value) in headers) {
      mockResponse.addHeader(key, value)
    }
    mockWebServer.enqueue(
        mockResponse.setBody(
            source.readString(StandardCharsets.UTF_8)))
  }

}