package com.ahmetkara.moviefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ahmetkara.moviefinder.adapter.GenreAdapter
import com.ahmetkara.moviefinder.data.ApiClient
import com.ahmetkara.moviefinder.databinding.ActivityMovieDetailBinding
import com.ahmetkara.moviefinder.model.MovieResult
import com.ahmetkara.moviefinder.model.detail.MovieDetailResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable()
    private lateinit var genreAdapter: GenreAdapter

    val movieDetails = MutableLiveData<MovieDetailResponse>()
    val loading = MutableLiveData<Boolean>()

    private var movie: MovieResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movie = intent.getSerializableExtra("movie_details") as MovieResult

        getMovieDetails(movie?.movieId)
        println("movieId: ${movie?.movieId}")
        println("path: ${movie?.backdrop_path}")
        observeLiveData()
    }


    private fun getMovieDetails(movieId:Int?){
        loading.value = true
        disposable.add(
            apiClient.getMovieDetails(movieId!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieDetailResponse>(){
                    override fun onSuccess(t: MovieDetailResponse) {
                        movieDetails.value = t
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        Log.i("Detail View Model" , "Hata : "+e.message)
                    }

                })
        )
    }

    private fun observeLiveData(){
        movieDetails.observe(this){
            it?.let {
                binding.content = it
                binding.detail = movie
                genreAdapter = GenreAdapter(it.genres!!)
                binding.recyclerviewGenres.adapter = genreAdapter
            }
        }
    }
}