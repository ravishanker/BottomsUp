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

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URLConnection


/**
 * OkHttp3 interceptor which provides a mock response from local resource file.
 */

class MockResponseInterceptor : Interceptor {

  private var context: Context? = null
  private var scenario: String? = null

  constructor(context: Context, scenario: String) {
    this.context = context.applicationContext
    this.scenario = scenario
  }

  constructor(context: Context) {
    this.context = context.applicationContext
  }

  fun setScenario(scenario: String) {
    this.scenario = scenario
  }

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {

    // Get resource ID for mock response file.
    val fileName = getFilename(chain.request(), scenario)
    val inputStream = context?.resources?.assets?.open(fileName)

    var mimeType = URLConnection.guessContentTypeFromStream(inputStream)
    if (mimeType == null) {
      mimeType = "application/json"
    }

    // Build and return mock response.
    return Response.Builder()
        .addHeader("content-type", mimeType)
        .body(ResponseBody.create(MediaType.parse(mimeType), toByteArray(inputStream)))
        .code(200)
        .message("Mock response from res/raw/$fileName")
        .protocol(Protocol.HTTP_1_0)
        .request(chain.request())
        .build()
  }

  //e.g. get_v2_beers, get_v2_beer
  @Throws(IOException::class)
  private fun getFilename(request: Request, scenario: String?): String {
    val requestedMethod = request.method()
    val prefix = if (scenario == null) "" else scenario + "_"
    val suffix = ".json"
    var filename = prefix + requestedMethod + request.url().url().path + suffix
    filename = filename.replace("/", "_").replace("-", "_").toLowerCase()
    Log.d("FileName", filename)
    return filename
  }

  @Throws(IOException::class)
  private fun toByteArray(inputStream: InputStream?): ByteArray {
    val output = ByteArrayOutputStream()

    output.use {
      val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
      var byteCount = inputStream?.read(buffer)
      while (byteCount != -1) {
        byteCount?.let { outputStream -> it.write(buffer, 0, outputStream) }
        byteCount = inputStream?.read(buffer)
      }
      return it.toByteArray()
    }
  }
}
