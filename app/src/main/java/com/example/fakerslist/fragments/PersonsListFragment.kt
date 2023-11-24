package com.example.fakerslist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fakerslist.ResponseState
import com.example.fakerslist.adapter.PersonsAdapter
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
    private lateinit var personsAdapter: PersonsAdapter

    @Inject
    lateinit var personMapperImpl: PersonMapperImpl

    private var personList = mutableListOf<Person>()

    val max=1000


    private val personsListViewModel: PersonsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentPersonsListBinding = FragmentPersonsListBinding.inflate(inflater, container, false)

        personListRefresher = fragmentPersonsListBinding.personListRefresh
        personsListRecycler = fragmentPersonsListBinding.personsListRecyclerView
        personsAdapter = PersonsAdapter(requireContext())

        personListRefresher.setOnRefreshListener {
            refreshData()
        }
        personsListViewModel.fetchPersons(1000)
        personsListViewModel.personLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Success -> {
                    personList = it.data.toMutableList()

                    personsAdapter.submitList(personList)
                    personListRefresher.isRefreshing = false
                    personsListRecycler.adapter = personsAdapter
                    personsListRecycler.layoutManager = LinearLayoutManager(requireContext())

                }

                is ResponseState.Error -> {}
                else -> {}
            }
        }

        return fragmentPersonsListBinding.root
    }

    private fun refreshData() {
        personsAdapter.submitList(emptyList()) // Clear the list
        personsListViewModel.fetchPersons(1000)
    }

}