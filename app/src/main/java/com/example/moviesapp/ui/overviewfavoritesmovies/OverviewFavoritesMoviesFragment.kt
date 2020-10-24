package com.example.moviesapp.ui.overviewfavoritesmovies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.databinding.FragmentOverviewFavoritesMoviesBinding
import com.example.moviesapp.ui.MainActivity
import com.example.moviesapp.ui.adapters.recyclerview.FavoritesMoviesRecyclerViewAdapter
import javax.inject.Inject

class OverviewFavoritesMoviesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val overviewFavoritesMoviesFragmentViewModel: OverviewFavoritesMoviesFragmentViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentOverviewFavoritesMoviesBinding.inflate(inflater)

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        overviewFavoritesMoviesFragmentViewModel.getFavoritesMovies().observe(viewLifecycleOwner, {favoritesMovies ->
            favoritesMovies?.let {movies ->

                if(movies.isEmpty()) {
                    binding.favoritesMoviesRecylerView.visibility = View.GONE
                    binding.emptyFavoritesMoviesTv.visibility = View.VISIBLE
                } else {
                    binding.favoritesMoviesRecylerView.adapter = FavoritesMoviesRecyclerViewAdapter(movies, FavoritesMoviesRecyclerViewAdapter.OnClickListener { selectedMovie ->
                        findNavController().navigate(OverviewFavoritesMoviesFragmentDirections.actionOverviewFavoritesMoviesFragmentToMovieDetailsFragment(selectedMovie.id))
                    })
                }
            }
        })

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}