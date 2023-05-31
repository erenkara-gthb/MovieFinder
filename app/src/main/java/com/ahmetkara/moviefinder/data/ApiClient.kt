package com.ahmetkara.moviefinder.data

import com.ahmetkara.moviefinder.util.Constant
import com.ahmetkara.moviefinder.model.MovieResponse
import com.ahmetkara.moviefinder.model.MovieResult
import com.ahmetkara.moviefinder.model.detail.MovieDetailResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val FIRST_PAGE = 1

class ApiClient {

    private val api = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(getOkhttpClient())
        .build()
        .create(ApiService::class.java)

    fun getTopRatedMovies(page : Int): Single<MovieResponse> {
        return api.getTopRatedMovies(page)
    }

    fun getPopularMovies(page : Int): Single<MovieResponse> {
        return api.getPopularMovies(page)
    }

    fun getUpcomingMovies(page : Int): Single<MovieResponse> {
        return api.getUpComingMovies(page)
    }

    fun getMovieDetails(movieId:Int): Single<MovieDetailResponse> {
        return api.getMovieDetails(movieId)
    }

    fun getFavMovies(movieId: Int) : Single<MovieResult> {
        return api.getFavoriMovie(movieId)
    }

    fun getSearchMovie(query: String): Single<MovieResponse>{
        return api.getSearchMovie(query)
    }




    private fun getOkhttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(RequestInterceptor())
        return client.build()
    }
}