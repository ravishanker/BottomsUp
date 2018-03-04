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

package com.raywenderlich.android.bottomsup.model

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * Beer.
 */

data class Beer(
        @Json(name = "servingTemperatureDisplay") val servingTemperatureDisplay: String?,
        @Json(name = "labels") val labels: Labels?,
        @Json(name = "style") val style: Style?,
        @Json(name = "status") val status: String?,
        @Json(name = "srmId") val srmId: String?,
        @Json(name = "beerVariationId") val beerVariationId: String?,
        @Json(name = "statusDisplay") val statusDisplay: String?,
        @Json(name = "foodPairings") val foodPairings: String?,
        @Json(name = "srm") val srm: List<Any?>?,
        @Json(name = "updateDate") val updateDate: String?,
        @Json(name = "servingTemperature") val servingTemperature: String?,
        @Json(name = "availableId") val availableId: Int?,
        @Json(name = "beerVariation") val beerVariation: List<Any?>?,
        @Json(name = "abv") val abv: String?,
        @Json(name = "year") val year: String?,
        @Json(name = "name") val name: String?,
        @Json(name = "id") val id: String?,
        @Json(name = "originalGravity") val originalGravity: String?,
        @Json(name = "styleId") val styleId: Int?,
        @Json(name = "ibu") val ibu: String?,
        @Json(name = "glasswareId") val glasswareId: Int?,
        @Json(name = "isOrganic") val isOrganic: String?,
        @Json(name = "createDate") val createDate: String?,
        @Json(name = "available") val available: Available?,
        @Json(name = "glass") val glass: Glass?,
        @Json(name = "description") val description: String?
): Serializable

data class Style(
        @Json(name = "id") val id: Int?,
        @Json(name = "category") val category: Category?,
        @Json(name = "description") val description: String?,
        @Json(name = "ibuMax") val ibuMax: String?,
        @Json(name = "srmMin") val srmMin: String?,
        @Json(name = "srmMax") val srmMax: String?,
        @Json(name = "ibuMin") val ibuMin: String?,
        @Json(name = "ogMax") val ogMax: String?,
        @Json(name = "fgMin") val fgMin: String?,
        @Json(name = "fgMax") val fgMax: String?,
        @Json(name = "createDate") val createDate: String?,
        @Json(name = "updateDate") val updateDate: String?,
        @Json(name = "abvMax") val abvMax: String?,
        @Json(name = "ogMin") val ogMin: String?,
        @Json(name = "abvMin") val abvMin: String?,
        @Json(name = "name") val name: String?,
        @Json(name = "categoryId") val categoryId: Int?
): Serializable

data class Category(
        @Json(name = "updateDate") val updateDate: String?,
        @Json(name = "id") val id: Int?,
        @Json(name = "description") val description: String?,
        @Json(name = "createDate") val createDate: String?,
        @Json(name = "name") val name: String?
): Serializable

data class Labels(
        @Json(name = "medium") val medium: String?,
        @Json(name = "large") val large: String?,
        @Json(name = "icon") val icon: String?
): Serializable

data class Glass(
        @Json(name = "updateDate") val updateDate: String?,
        @Json(name = "id") val id: Int?,
        @Json(name = "description") val description: String?,
        @Json(name = "createDate") val createDate: String?,
        @Json(name = "name") val name: String?
): Serializable

data class Available(
        @Json(name = "description") val description: String?,
        @Json(name = "name") val name: String?
): Serializable