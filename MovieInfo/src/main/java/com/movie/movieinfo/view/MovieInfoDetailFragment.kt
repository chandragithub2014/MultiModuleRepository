package com.movie.movieinfo.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.movie.core.base.BaseFragment
import com.movie.core.roomDB.Movie
import com.movie.movieinfo.R
import com.movie.movieinfo.viewmodel.MovieDataViewModel
import kotlinx.android.synthetic.main.fragment_movie_info_detail.*
import kotlinx.android.synthetic.main.fragment_movie_info_detail.view.*
import kotlinx.android.synthetic.main.fragment_movie_list.progressBar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "imdbId"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieInfoDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieInfoDetailFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var mContainerId: Int = -1
    private var movieListDetailView: View? = null
    lateinit var toolbar: Toolbar
    private val movieViewModel by activityViewModels<MovieDataViewModel>()
    var movie : Movie = Movie("","","","","",false,"","")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            println("Param1 Received $param1")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        movieListDetailView =  inflater.inflate(R.layout.fragment_movie_info_detail, container, false)


        return movieListDetailView
    }

    override fun onResume() {
        super.onResume()
        if( !TextUtils.isEmpty(param1)){
            movieViewModel.fetchMovieById(param1!!)
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolBar) as Toolbar
        //setHasOptionsMenu(true)
        view.movie_fav.setOnCheckedChangeListener { compoundButton, b ->

            param1?.let {
                movieViewModel.updateFavouriteMovie(b, it)
            }
        }
        observeViewModel()
    }

    private fun observeViewModel(){
        movieViewModel.fetchLoadStatus().observe(viewLifecycleOwner, Observer {
            if (!it) {
                println(it)
                progressBar.visibility = View.GONE
            }else{
                progressBar.visibility = View.VISIBLE
            }
        })

        movieViewModel.fetchError().observe(viewLifecycleOwner, Observer {
            it?.let {
                if (!TextUtils.isEmpty(it)) {
                    Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
                }
            }
        })

        movieViewModel.fetchMovieDetail().observe(viewLifecycleOwner, Observer {
            it?.let {
                movie = it
                movie_name.text = it.Title
                movie_type.text = it.Type
                movie_fav.isChecked = it.favourite
                Glide
                    .with(this)
                    .load(it.Poster)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher)
                    .into(imageView);

                toolbar?.let { it1 -> initToolBar(it1,it.Title) }
            }
        })
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MovieInfoDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MovieInfoDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}