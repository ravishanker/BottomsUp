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

package com.raywenderlich.android.bottomsup.data.database

import com.raywenderlich.android.bottomsup.model.Beer
import com.raywenderlich.android.bottomsup.util.LiveDataTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.isA
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class BeerDaoTest : DbTest() {

    @Test @Throws(InterruptedException::class)
    fun testInsertAndLoad() {

        db.beerDao().insert(beerList())

        val beers =  LiveDataTestUtil.getValue(db.beerDao().beers())

        assertThat(beers.size, `is`(2))
        assertThat(beers.first(), isA(Beer::class.java))
        assertThat(beers.first().id, `is`("abc"))
        assertThat(beers.last().id, `is`("def"))
    }

    private fun beerList(): List<Beer> {
        val beer1 = Beer("abc", "Beer1", "First Beer")
        val beer2 = Beer("def", "Beer2", "Second Beer")
        return listOf(beer1, beer2)
    }

}