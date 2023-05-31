package com.ahmetkara.moviefinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmetkara.moviefinder.data.ApiClient
import com.ahmetkara.moviefinder.data.FIRST_PAGE
import com.ahmetkara.moviefinder.databinding.ActivityMainBinding
import com.ahmetkara.moviefinder.model.MovieResponse
import com.ahmetkara.moviefinder.model.MovieResult
import com.ahmetkara.moviefinder.adapter.MovieAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var page = FIRST_PAGE

    private val apiClient = ApiClient()
    private val disposable = CompositeDisposable()
    val movieList = MutableLiveData<List<MovieResult>>()
    val loadingPopularMovie = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMovies()
        handleClick()
        observeLiveData()
    }

    private fun handleClick(){
        binding.nextPageBt.setOnClickListener {
            nextPage()
        }
        binding.previousPageBt.setOnClickListener {
            if (page > 1){
                previousPage()
            }
        }

        binding.svInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.nextPageBt.visibility = View.GONE
                binding.previousPageBt.visibility = View.GONE
                binding.ivSearchOff.visibility = View.VISIBLE
                getSearchMovies(binding.svInput.query.toString())
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })

        binding.ivSearchOff.setOnClickListener {
            binding.nextPageBt.visibility = View.VISIBLE
            binding.previousPageBt.visibility = View.VISIBLE
            binding.ivSearchOff.visibility = View.GONE
            binding.svInput.setQuery("",false)
            getMovies()
        }
    }

    private fun getMovies(){
        loadingPopularMovie.value = true
        disposable.add(
            apiClient.getPopularMovies(FIRST_PAGE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResponse>(){
                    override fun onSuccess(t: MovieResponse) {
                        movieList.value = t.results
                        loadingPopularMovie.value = false
                        Log.i("POPULAR FRAGMENT : ", "POPULAR WORKING")
                    }

                    override fun onError(e: Throwable) {
                        Log.i("POPULAR FRAGMENT : ", "POPULAR ERROR"+e.message)
                    }

                })
        )
    }

    private fun getSearchMovies(query:String){
        loadingPopularMovie.value = true
        disposable.add(
            apiClient.getSearchMovie(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResponse>(){
                    override fun onSuccess(t: MovieResponse) {
                        movieList.value = t.results
                        loadingPopularMovie.value = false
                        Log.i("POPULAR FRAGMENT : ", "POPULAR WORKING")
                    }

                    override fun onError(e: Throwable) {
                        Log.i("POPULAR FRAGMENT : ", "POPULAR ERROR"+e.message)
                    }

                })
        )
    }

    private fun nextPage(){
        loadingPopularMovie.value = true
        disposable.add(
            apiClient.getPopularMovies(page+1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResponse>(){
                    override fun onSuccess(t: MovieResponse) {
                        movieList.value = t.results
                        loadingPopularMovie.value = false
                        Log.i("POPULAR FRAGMENT : ", "POPULAR WORKING")
                    }

                    override fun onError(e: Throwable) {
                        Log.i("POPULAR FRAGMENT : ", "UPCOMING ERROR"+e.message)
                    }

                })
        )
        page += 1
    }

    private fun previousPage(){
        loadingPopularMovie.value = true
        disposable.add(
            apiClient.getPopularMovies(page-1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResponse>(){
                    override fun onSuccess(t: MovieResponse) {
                        movieList.value = t.results
                        loadingPopularMovie.value = false
                        Log.i("POPULAR FRAGMENT : ", "POPULAR WORKING")
                    }

                    override fun onError(e: Throwable) {
                        Log.i("POPULAR FRAGMENT : ", "UPCOMING ERROR"+e.message)
                    }

                })
        )
        page -= 1
    }

    private fun observeLiveData(){
        movieList.observe(this){
            it?.let {
                binding.recyclerViewPopular.visibility = View.VISIBLE
                binding.recyclerViewPopular.layoutManager = GridLayoutManager(this,3)
                binding.recyclerViewPopular.adapter = MovieAdapter(it){
                    val intent = Intent(this, MovieDetailActivity::class.java)
                    intent.putExtra("movie_details",it)
                    startActivity(intent)
                }
            }
        }

        loadingPopularMovie.observe(this) { loadingTopRated ->
            loadingTopRated?.let {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

}