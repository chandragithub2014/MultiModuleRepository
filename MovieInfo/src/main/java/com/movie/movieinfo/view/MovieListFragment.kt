package com.movie.movieinfo.view

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.movie.movieinfo.R
import com.movie.movieinfo.viewmodel.MovieDataViewModel
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MovieListFragment : Fragment(),LifecycleOwner {
    // TODO: Rename and change types of parameters
   // private val movieViewModel: MovieDataViewModel by viewModels()
    private val movieViewModel by activityViewModels<MovieDataViewModel>()
    private var param1: String? = null
    private var param2: String? = null
    private var movieListView: View? = null
    var mContainerId: Int = -1
    private var movieListAdapter: MovieListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        movieListView =  inflater.inflate(R.layout.fragment_movie_list, container, false)
        return movieListView
    }

    override fun onResume() {
        super.onResume()
        movieViewModel.fetchMovieData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeViewModel()
    }
    private fun observeViewModel() {
        movieViewModel.fetchMoviesLiveData().observe(viewLifecycleOwner, Observer {
            it?.let {
                println("Response From Network :::: $it")
                 movieListAdapter?.refreshAdapter(it.Search)
            }
        })

        movieViewModel.fetchLoadStatus().observe(viewLifecycleOwner, Observer {
            if (!it) {
                println(it)
                progressBar.visibility = View.GONE
            }
        })

        movieViewModel.fetchError().observe(viewLifecycleOwner, Observer {
            it?.let {
                if (!TextUtils.isEmpty(it)) {
                    Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun initAdapter() {
        movieListAdapter = MovieListAdapter(this@MovieListFragment.requireActivity(),arrayListOf())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieListAdapter
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MovieListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MovieListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}