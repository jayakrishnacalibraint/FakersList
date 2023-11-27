package com.example.fakerslist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fakerslist.R
import com.example.fakerslist.ResponseState
import com.example.fakerslist.adapter.PersonsAdapter
import com.example.fakerslist.databinding.FragmentPersonsListBinding
import com.example.fakerslist.model.PersonData
import com.example.fakerslist.viewmodel.PersonsListViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonsListFragment : Fragment() {

    private lateinit var fragmentPersonsListBinding: FragmentPersonsListBinding
    private lateinit var personsListRecycler: RecyclerView
    private lateinit var personListRefresher: SwipeRefreshLayout
    private lateinit var personsAdapter: PersonsAdapter
    private lateinit var shimmerLayout: ShimmerFrameLayout
    private lateinit var toolbar: Toolbar

    private var personList = mutableListOf<PersonData>()

    private val initialFetch=10

    private val personsListViewModel: PersonsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentPersonsListBinding = FragmentPersonsListBinding.inflate(inflater, container, false)
        initializeViews()


        personsAdapter = PersonsAdapter(requireContext())

        personListRefresher.setOnRefreshListener {
            refreshData()
        }
        personsListRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItemPosition == totalItemCount - 1) {

                    Toast.makeText(requireContext(), "End of list", Toast.LENGTH_SHORT).show()
                }
            }
        })

        //fetch the data
        personsListViewModel.fetchPersons(initialFetch)

        //observe the data
        personsListViewModel.personLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Success -> {
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
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

    private fun initializeViews() {
        toolbar = fragmentPersonsListBinding.toolbar
        setToolbar()


        personListRefresher = fragmentPersonsListBinding.personListRefresh
        personsListRecycler = fragmentPersonsListBinding.personsListRecyclerView
        shimmerLayout = fragmentPersonsListBinding.shimmerLayout
    }

    private fun setToolbar() {
        val fragmentActivity: AppCompatActivity = requireActivity() as AppCompatActivity
        fragmentActivity.setSupportActionBar(toolbar)
        fragmentActivity.supportActionBar?.apply {
            title = getString(R.string.persons_list)
        }

    }

    private fun refreshData() {
        personsAdapter.submitList(emptyList())
        personsListViewModel.fetchPersons(initialFetch)
    }

}