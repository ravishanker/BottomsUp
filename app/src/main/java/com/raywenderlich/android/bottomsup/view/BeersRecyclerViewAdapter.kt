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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.raywenderlich.android.bottomsup.R
import com.raywenderlich.android.bottomsup.model.Beer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.beer_list_content.view.*

/**
 * Beers Recycler View Adapter.
 */

class BeersRecyclerViewAdapter(private val parentActivity: BeerListActivity,
                               private val values: List<Beer>,
                               private val twoPane: Boolean) :
        RecyclerView.Adapter<BeersRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private lateinit var context: Context

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Beer
            if (twoPane) {
                val fragment = BeerDetailFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(BeerDetailFragment.ARG_BEER, item)
                    }
                }
                parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.beer_detail_container, fragment)
                        .commit()
            } else {
                val intent = Intent(v.context, BeerDetailActivity::class.java).apply {
                    putExtra(BeerDetailFragment.ARG_BEER, item)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.beer_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.showBeerView(item)

        with(holder.itemView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int {
        return values.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val beerNameView: TextView = view.beerName
        private val beerIconView: ImageView = view.beerImage
        private val beerDescriptionView: TextView = view.beerDescription

        fun showBeerView(beer: Beer) {
            Picasso.with(context)
                    .load(beer.labels!!.icon)
                    .placeholder(R.drawable.ic_beer_black_24dp)
                    .into(beerIconView)

            beerNameView.text = beer.name
            beerDescriptionView.text = beer.description
        }
    }
}