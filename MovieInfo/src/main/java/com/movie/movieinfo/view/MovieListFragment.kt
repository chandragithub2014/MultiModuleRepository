package com.movie.movieinfo.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.movie.core.base.BaseFragment
import com.movie.core.basemodel.MenuItem
import com.movie.movieinfo.R
import com.movie.movieinfo.viewmodel.MovieDataViewModel
import com.mvvm.appnavigator.replaceFragment
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
class MovieListFragment : BaseFragment(),LifecycleOwner,OnItemClickListner {
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
        mContainerId = container?.id?:-1
        return movieListView
    }

    override fun onResume() {
        super.onResume()
        prepareAndSetMenuList()
        //movieViewModel.fetchMovieData()
        movieViewModel.fetchMovieDataByTitleASC()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: Toolbar = view.findViewById(R.id.toolBar) as Toolbar
        //setHasOptionsMenu(true)
        initToolBar(toolbar,"Movie List")
        initAdapter()
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

    }


  /*  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu!!, inflater)
    }*/


    private fun observeViewModel() {
        Log.d("MovieListFragment","In Observe View Model")
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
                }else{
                    observeResponse()
                }
            }
        })
    }

    private fun observeResponse(){
        movieViewModel.movieListFromLocal.observe(viewLifecycleOwner, Observer {
            it?.let {
                println("Response From LocalDb :::: $it")
                if(it.size > 0 ) {
                    movieListAdapter?.refreshAdapter(it)

                }else{
                    progressBar.visibility = View.VISIBLE
                    movieViewModel.fetchMovieData()

                }

            }
        })
    }
    private fun initAdapter() {
        movieListAdapter = MovieListAdapter(this@MovieListFragment.requireActivity(),arrayListOf(),this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieListAdapter
        }
    }

    override fun useToolbar(): Boolean {
        return true
    }
    private val MENU_SORT_BY_YEAR = Menu.FIRST
    private val MENU_SORT_BY_TITLE_DESC: Int = Menu.FIRST + 1
    private val MENU_SORT_BY_RATING: Int = Menu.FIRST + 2

    private fun prepareAndSetMenuList(){
        val menuList = mutableListOf<MenuItem>()
        menuList.add(MenuItem(MENU_SORT_BY_YEAR,"Sort By Year"))
        menuList.add(MenuItem(MENU_SORT_BY_TITLE_DESC,"Sort By Title"))
        menuList.add(MenuItem(MENU_SORT_BY_RATING,"Sort By Rating"))
        setMenuList(menuList)

    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            MENU_SORT_BY_YEAR -> {
                movieViewModel.fetchMovieDataByYearASC()
                observeResponse()
            }
            MENU_SORT_BY_TITLE_DESC -> {
                movieViewModel.fetchMovieDataByTitleDesc()
                observeResponse()
            }
            MENU_SORT_BY_RATING -> {
                movieViewModel.fetchMovieDataByImdbASC()
                observeResponse()
            }


        }
        return false
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

    override fun navigateToDetail(rating: String) {
        if(!TextUtils.isEmpty(rating)) {
            var movieInfoDetailFragment = MovieInfoDetailFragment()
            val mArgs = Bundle()
            mArgs.putString("imdbId", rating)
            movieInfoDetailFragment.arguments = mArgs
            activity?.replaceFragment(movieInfoDetailFragment, mContainerId)
        }
    }
}