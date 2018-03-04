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

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.raywenderlich.android.bottomsup.R
import com.raywenderlich.android.bottomsup.model.Beer
import com.raywenderlich.android.bottomsup.viewmodel.BeerViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_beer_list.*
import kotlinx.android.synthetic.main.beer_list.*
import javax.inject.Inject


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [BeerDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class BeerListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false

    @Inject lateinit var beerViewModel: BeerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_beer_list)
        setSupportActionBar(toolbar)
        toolbar.title = title

        if (beer_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }

        beerViewModel.getBeers().observe(this, Observer {
            it?.let {
                Log.d("Beers Live", it.toString())
                setupRecyclerView(beer_list, it)
            }
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, beers: List<Beer>) {
        recyclerView.adapter = BeersRecyclerViewAdapter(this, beers, mTwoPane)
    }
}
