package com.example.moviesapp.ui.moviedetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.R
import com.example.moviesapp.data.database.toFavoriteMovie
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.databinding.FragmentMovieDetailsBinding
import com.example.moviesapp.ui.MainActivity
import com.example.moviesapp.utils.enums.Status
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val moviesFragmentViewModel: MovieDetailsFragmentViewModel by viewModels { viewModelFactory }

    private lateinit var movie: Movie

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMovieDetailsBinding.inflate(inflater)

        val movieId = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movieId

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        moviesFragmentViewModel.isFavorite.observe(viewLifecycleOwner, {isFavorite ->
            if(isFavorite) {
                binding.addFavoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.addFavoriteButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        })

        moviesFragmentViewModel.getFavoriteMovieById(movieId).observe(viewLifecycleOwner, { favoriteMovie ->
            favoriteMovie?.let {
                moviesFragmentViewModel.isFavorite.value = true
            }
        })

        moviesFragmentViewModel.getMovieDetailsById(movieId).observe(viewLifecycleOwner,
            { response ->
                when(response.status) {
                    Status.LOADING -> {
                        binding.progressBarMovieDetails.visibility = View.VISIBLE
                        binding.container.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.progressBarMovieDetails.visibility = View.GONE
                        binding.container.visibility = View.VISIBLE
                        response.data?.let { movieResponse ->
                            movie = movieResponse
                            binding.movie = movieResponse
                            binding.rating.rating = movieResponse.voteAverage.toFloat() / 2
                        }
                    }
                    Status.ERROR -> {
                        binding.progressBarMovieDetails.visibility = View.GONE
                        binding.container.visibility = View.GONE
                        binding.errorMovieDetailsTv.visibility = View.VISIBLE
                        binding.addFavoriteButton.hide()
                    }
                }
            }
        )

        binding.addFavoriteButton.setOnClickListener {
            moviesFragmentViewModel.isFavorite.value?.let { favorite ->
                if(favorite) {
                    moviesFragmentViewModel.deleteFavoriteMovie(movieId)
                } else {
                    moviesFragmentViewModel.insertFavoriteMovie(movie.toFavoriteMovie())
                }
            }
        }

        return binding.root
    }

}