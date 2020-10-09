package com.example.moviesapp.ui.overviewmovies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.R
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.databinding.FragmentOverviewMoviesBinding
import com.example.moviesapp.ui.MainActivity
import com.example.moviesapp.ui.adapters.recyclerview.MoviesRecyclerViewAdapter
import javax.inject.Inject

class OverviewMoviesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<OverviewMoviesFragmentViewModel> { viewModelFactory }
    private var listMovies: ArrayList<Movie> = arrayListOf()
    private var adapter: MoviesRecyclerViewAdapter = MoviesRecyclerViewAdapter(listOf(), MoviesRecyclerViewAdapter.OnClickListener{})
    private var currentPage = 1

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOverviewMoviesBinding.inflate(inflater)

        if(listMovies.isEmpty())
            getMoviesNowPlaying(binding)
        else
            binding.moviesRecylerView.adapter = adapter

        binding.mainToolbar.inflateMenu(R.menu.main_menu)
        binding.mainToolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            item?.let { menuItem ->
                when(menuItem.itemId) {
                    R.id.item_favorites -> {
                        findNavController().navigate(OverviewMoviesFragmentDirections.actionOverviewMoviesFragmentToOverviewFavoritesMoviesFragment())
                    }
                    R.id.refresh -> {
                        binding.buttonShowMore.visibility = View.GONE
                        getMoviesNowPlaying(binding)
                    }
                    R.id.app_bar_search -> {
                        val searchView = menuItem.actionView as SearchView
                        searchView.onActionViewExpanded()
                        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                if (query != null && query.trim().isNotEmpty())
                                    getSearchResult(query, binding)
                                return true
                            }
                            override fun onQueryTextChange(newText: String?): Boolean = true
                        })
                        menuItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
                            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                                return true
                            }

                            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                                binding.buttonShowMore.visibility = View.VISIBLE
                                binding.moviesRecylerView.adapter = adapter
                                return true
                            }
                        })
                    }
                    else -> {}
                }
            }
            return@OnMenuItemClickListener true
        })

        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if(isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.genericErrorTv.visibility = View.GONE
                binding.moviesRecylerView.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.moviesRecylerView.visibility = View.VISIBLE
            }
        })

        viewModel.hasError.observe(viewLifecycleOwner, { hasError ->
            if(hasError) {
                binding.progressBar.visibility = View.GONE
                binding.moviesRecylerView.visibility = View.GONE
                binding.genericErrorTv.visibility = View.VISIBLE
            }
        })

        binding.buttonShowMore.setOnClickListener {
            loadMore(binding)
        }

        return binding.root
    }

    private fun getMoviesNowPlaying(binding: FragmentOverviewMoviesBinding) {
        viewModel.getMoviesNowPlaying().observe(viewLifecycleOwner,
            { response ->
                response?.let { movies ->
                    listMovies.addAll(movies)
                    adapter = MoviesRecyclerViewAdapter(listMovies, MoviesRecyclerViewAdapter.OnClickListener { selectedMovie ->
                        findNavController().navigate(OverviewMoviesFragmentDirections.actionOverviewMoviesFragmentToMovieDetailsFragment(
                            selectedMovie.id
                        ))
                    })
                    binding.moviesRecylerView.adapter = adapter
                    binding.buttonShowMore.visibility = View.VISIBLE
                }
            }
        )
    }

    private fun loadMore(binding: FragmentOverviewMoviesBinding) {
        if(currentPage++ >= 1000) return

        viewModel.getMoviesNowPlaying(currentPage.toString(), loadMore = true).observe(viewLifecycleOwner,
            { response ->
                binding.progressBar.visibility = View.GONE
                response?.let { movies ->
                    listMovies.addAll(movies)
                    adapter.notifyDataSetChanged()
                    binding.buttonShowMore.visibility = View.VISIBLE
                }
            }
        )
    }

    private fun getSearchResult(query: String, binding: FragmentOverviewMoviesBinding) {
        viewModel.getSearchResult(query).observe(viewLifecycleOwner,
            { response ->
                binding.progressBar.visibility = View.GONE
                binding.moviesRecylerView.visibility = View.VISIBLE
                binding.buttonShowMore.visibility = View.GONE
                response?.let {movies ->
                    binding.moviesRecylerView.adapter = MoviesRecyclerViewAdapter(movies, MoviesRecyclerViewAdapter.OnClickListener { selectedMovie ->
                        findNavController().navigate(OverviewMoviesFragmentDirections.actionOverviewMoviesFragmentToMovieDetailsFragment(
                            selectedMovie.id
                        ))
                    })
                }
            }
        )
    }

}