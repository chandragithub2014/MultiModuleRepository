package com.movie.core.base

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.movie.core.R
import com.movie.core.basemodel.MenuItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open  class BaseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var menuItemList = mutableListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(useToolbar())
    }
     fun initToolBar(toolbar : Toolbar,title : String){
          //set toolbar appearance
        //set toolbar appearance
        toolbar.setBackgroundColor(Color.CYAN)
        var toolBarTitle : TextView = toolbar.findViewById(R.id.toolbar_title)
        toolBarTitle.text = title
        //for crate home button
        //for crate home button
        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(toolbar)
        activity?.let{
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            activity.supportActionBar?.title = ""
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear();
         //menu.add(0, MENU_ADD, Menu.NONE, R.string.your-add-text).setIcon(R.drawable.your-add-icon);
        for(menuItem in menuItemList){
            menu.add(menuItem.itemName)
        }
        super.onPrepareOptionsMenu(menu)
    }
    protected open fun useToolbar(): Boolean {
        return true
    }

    fun setMenuList(menuItemList : MutableList<MenuItem>){
        this.menuItemList  = menuItemList
    }
}