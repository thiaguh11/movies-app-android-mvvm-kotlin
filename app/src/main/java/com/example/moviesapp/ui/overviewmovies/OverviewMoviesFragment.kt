package com.example.moviesapp.ui.overviewmovies

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.widget.NestedScrollView
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
    lateinit var binding: FragmentOverviewMoviesBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOverviewMoviesBinding.inflate(inflater)

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        if(listMovies.isEmpty())
            getMoviesNowPlaying()
        else
            binding.moviesRecylerView.adapter = adapter

        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { view, _, scrollY, _, _ ->
            if(scrollY == view.getChildAt(0).measuredHeight - view.measuredHeight) {
                loadMore()
            }
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

        viewModel.isLoadingShowMore.observe(viewLifecycleOwner, { isLoadingShowMore ->
            binding.progressBarShowMore.visibility = if(isLoadingShowMore) View.VISIBLE else View.GONE
        })

        viewModel.hasError.observe(viewLifecycleOwner, { hasError ->
            if(hasError) {
                binding.progressBar.visibility = View.GONE
                binding.moviesRecylerView.visibility = View.GONE
                binding.genericErrorTv.visibility = View.VISIBLE
            }
        })

        return binding.root
    }

    private fun getMoviesNowPlaying() {
        viewModel.getMoviesNowPlaying().observe(viewLifecycleOwner,
            { response ->
                response?.let { movies ->
                    listMovies.clear()
                    listMovies.addAll(movies)
                    adapter = MoviesRecyclerViewAdapter(listMovies, MoviesRecyclerViewAdapter.OnClickListener { selectedMovie ->
                        findNavController().navigate(OverviewMoviesFragmentDirections.actionOverviewMoviesFragmentToMovieDetailsFragment(
                            selectedMovie.id
                        ))
                    })
                    binding.moviesRecylerView.adapter = adapter
                }
            }
        )
    }

    private fun loadMore() {
        if(currentPage++ >= 1000) return
        viewModel.getMoviesNowPlaying(currentPage.toString(), loadMore = true).observe(viewLifecycleOwner,
            { response ->
                response?.let { movies ->
                    listMovies.addAll(movies)
                    adapter.notifyDataSetChanged()
                }
            }
        )
    }

    private fun getSearchResult(query: String) {
        viewModel.getSearchResult(query).observe(viewLifecycleOwner,
            { response ->
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.refresh -> {
                getMoviesNowPlaying()
            }
            R.id.item_favorites -> {
                findNavController().navigate(OverviewMoviesFragmentDirections.actionOverviewMoviesFragmentToOverviewFavoritesMoviesFragment())
            }
            R.id.app_bar_search -> {
                val searchView = item.actionView as SearchView
                searchView.onActionViewExpanded()
                searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null && query.trim().isNotEmpty()){
                            binding.progressBarShowMore.visibility = View.GONE
                            getSearchResult(query)
                        }
                        return true
                    }
                    override fun onQueryTextChange(newText: String?): Boolean = true
                })
                item.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
                    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                        return true
                    }

                    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                        binding.moviesRecylerView.adapter = adapter
                        return true
                    }
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
