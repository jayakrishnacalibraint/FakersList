package com.example.fakerslist.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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
    private lateinit var lazyDownProgressBar: ProgressBar
    private lateinit var lazyUpProgressBar: ProgressBar

  private var dataFetched =false
    private var personList = mutableListOf<PersonData>()

    private val totalFetch = 1000

    private val personsListViewModel: PersonsListViewModel by viewModels()
    private var recyclerViewState: Parcelable? = null
    private lateinit var layoutManager :LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentPersonsListBinding = FragmentPersonsListBinding.inflate(inflater, container, false)
        initializeViews()

        var refresh = false

        personsAdapter = PersonsAdapter(requireContext())
        layoutManager=LinearLayoutManager(requireContext())
        personListRefresher.setOnRefreshListener {
            refresh = true
            refreshData()
        }
        val itemsPerPage = 20
        var isProgressBarVisible = false
        var lastVisibleItemPosition = 0
        personsListRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val currentVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                val isScrollingDown = dy > 0

                if (currentVisibleItemPosition > lastVisibleItemPosition) {
                    if (isScrollingDown && (currentVisibleItemPosition + 1) % itemsPerPage == 0 && !isProgressBarVisible) {
                        // Display progress bar after every 20 items
                        isProgressBarVisible = true
                        showProgressBar()

                        // Simulate loading for a particular time
                        recyclerView.postDelayed({
                            hideProgressBar()
                            isProgressBarVisible = false
                        }, 500) // Adjust the time as needed
                    }

                } else if (currentVisibleItemPosition < lastVisibleItemPosition) {
                    if (!isScrollingDown && (currentVisibleItemPosition + 1) % itemsPerPage == 0 && !isProgressBarVisible) {
                        // Display progress bar after every 20 items
                        isProgressBarVisible = true
                        showUpProgressBar()

                        // Simulate loading for a particular time
                        recyclerView.postDelayed({
                            hideUpProgressBar()
                            isProgressBarVisible = false
                        }, 500) // Adjust the time as needed
                    }

                }
                lastVisibleItemPosition = currentVisibleItemPosition
                if ((currentVisibleItemPosition == totalItemCount - 1) && !refresh) {

                    Toast.makeText(requireContext(), "End of list", Toast.LENGTH_SHORT).show()

                }


            }

        })

        //fetch the data
        if (!dataFetched)
        {
        personsListViewModel.fetchPersons(totalFetch)}

        //observe the data
        personsListViewModel.personLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Success -> {
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    personList = it.data.toMutableList()
                    personsAdapter.submitList(personList)
                    personListRefresher.isRefreshing = false
                    refresh = false
                    personsListRecycler.adapter = personsAdapter
                    personsListRecycler.layoutManager =layoutManager
                    dataFetched=true
                }

                is ResponseState.Error -> {
                    Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {

                }
            }
        }

        return fragmentPersonsListBinding.root
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        recyclerViewState = layoutManager.onSaveInstanceState()
        outState.putParcelable("recycler_state", recyclerViewState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable("recycler_state")
        }
    }

    override fun onResume() {
        super.onResume()
        // Restore RecyclerView state when coming back to the fragment
        if (recyclerViewState != null) {
            layoutManager.onRestoreInstanceState(recyclerViewState)
        }
    }


    private fun hideUpProgressBar() {
        lazyUpProgressBar.visibility = View.GONE
    }

    private fun showUpProgressBar() {
        lazyUpProgressBar.visibility = View.VISIBLE

    }

    private fun hideProgressBar() {
        lazyDownProgressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        lazyDownProgressBar.visibility = View.VISIBLE

    }

    private fun initializeViews() {
        toolbar = fragmentPersonsListBinding.toolbar
        setToolbar()

        lazyDownProgressBar = fragmentPersonsListBinding.lazyDownProgressBar
        lazyUpProgressBar = fragmentPersonsListBinding.lazyUpProgressBar
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
        personsListViewModel.fetchPersons(totalFetch)
    }

}