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

package com.raywenderlich.android.bottomsup.di

import android.app.Application
import android.arch.persistence.room.Room
import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.raywenderlich.android.bottomsup.data.api.BreweryDbApiService
import com.raywenderlich.android.bottomsup.data.api.MockResponseInterceptor
import com.raywenderlich.android.bottomsup.data.database.BeerDao
import com.raywenderlich.android.bottomsup.data.database.BeerDatabase
import com.raywenderlich.android.bottomsup.data.repository.BreweryDbRepository
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
open class AppModule(val app: Application) {

  @Provides
  @Singleton
  fun provideApplication(): Application = app

  @Provides
  open fun provideMockResponseInterceptor() = MockResponseInterceptor(app) //comment this out when using real api

  @Provides
  fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
    HttpLoggingInterceptor.Logger { Log.d("BeerApi: ", it) }
  }

  @Singleton
  @Provides
  open fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor,
                               mockResponseInterceptor: MockResponseInterceptor): OkHttpClient =
      OkHttpClient.Builder()
          .addInterceptor(mockResponseInterceptor) //comment this out when using real api
          .addNetworkInterceptor(StethoInterceptor())
          .addInterceptor(loggingInterceptor)
          .build()

  @Singleton
  @Provides
  fun provideMoshiConverter(): MoshiConverterFactory =
      MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())

  @Singleton
  @Provides
  fun provideRetrofitClient(client: OkHttpClient, converter: MoshiConverterFactory): Retrofit =
      Retrofit.Builder()
          .client(client)
          .addConverterFactory(converter)
          .baseUrl("http://api.brewerydb.com/v2/")
          .build()

  @Singleton
  @Provides
  fun provideBreweryDbApiService(retrofit: Retrofit): BreweryDbApiService =
      retrofit.create(BreweryDbApiService::class.java)

  @Provides
  fun provideExecutor(): Executor = Executors.newSingleThreadExecutor()


  @Singleton
  @Provides
  fun provideBreweryDbRepository(service: BreweryDbApiService,
                                 dao: BeerDao,
                                 executor: Executor): BreweryDbRepository =
      BreweryDbRepository(service, dao, executor)

  @Singleton
  @Provides
  fun provideBeerDatabase(): BeerDatabase =
      Room.databaseBuilder(app, BeerDatabase::class.java, "beers_db")
          .fallbackToDestructiveMigration()
          .build()

  @Singleton
  @Provides
  fun provideBeerDataAccessObject(db: BeerDatabase): BeerDao = db.beerDao()

}