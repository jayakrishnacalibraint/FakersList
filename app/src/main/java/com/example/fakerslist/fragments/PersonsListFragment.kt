package com.example.fakerslist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fakerslist.databinding.FragmentPersonsListBinding
import com.example.fakerslist.mapper.PersonMapperImpl
import com.example.fakerslist.model.Person
import com.example.fakerslist.viewmodel.PersonsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PersonsListFragment : Fragment() {

    private lateinit var fragmentPersonsListBinding: FragmentPersonsListBinding
    private lateinit var personsListRecycler: RecyclerView
    private lateinit var personListRefresher: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentPersonsListBinding = FragmentPersonsListBinding.inflate(inflater, container, false)

        personListRefresher = fragmentPersonsListBinding.personListRefresh
        personsListRecycler = fragmentPersonsListBinding.personsListRecyclerView


        return fragmentPersonsListBinding.root
    }

}