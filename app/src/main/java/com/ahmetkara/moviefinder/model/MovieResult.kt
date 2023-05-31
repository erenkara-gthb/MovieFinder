package com.ahmetkara.moviefinder.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MovieResult(

    @PrimaryKey
    @SerializedName("id")
    var movieId: Int,

    @SerializedName("runtime")
    var runtime: Int,

    @SerializedName("revenue")
    var revenue : Long,

    @SerializedName("tagline")
    var tagline : String?=null,

    @SerializedName("poster_path")
    var poster_path: String? = null,

    @SerializedName("overview")
    var overview: String? = null,

    @SerializedName("release_date")
    var release_date: String? = null,

    @SerializedName("original_title")
    var original_title: String? = null,

    @SerializedName("original_language")
    var original_language: String? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("backdrop_path")
    var backdrop_path: String? = null,

    @SerializedName("popularity")
    var popularity: Double,

    @SerializedName("vote_count")
    var vote_count: Int,

    @SerializedName("vote_average")
    var vote_average: Double

) :java.io.Serializable
