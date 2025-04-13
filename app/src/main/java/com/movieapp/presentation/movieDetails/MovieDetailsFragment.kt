package com.movieapp.presentation.movieDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.movieapp.R
import com.movieapp.databinding.FragmentMovieDetailsBinding
import com.movieapp.domain.dto.MovieDto
import com.movieapp.utils.Keys
import com.movieapp.utils.loadImage
import com.movieapp.utils.serializable
import com.movieapp.utils.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import mohsen.soltanian.cleanarchitecture.utils.Genres

@AndroidEntryPoint
class MovieDetailsFragment :
    BaseFragment<FragmentMovieDetailsBinding>(FragmentMovieDetailsBinding::inflate) {

    override val viewModel: MovieDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        viewModel.movie.value =
            MovieDetailsViewStates.MovieDetails(arguments?.serializable<MovieDto>(Keys.MOVIE) as MovieDto)
        val movie = (viewModel.movie.value as MovieDetailsViewStates.MovieDetails).movie

        var genresStr = ""

        binding.apply {
            titleTV.text = movie.title
            overViewTV.text = movie.overview
            ratingTV.text = movie.voteAverage.toString()
            releaseDateTV.text = movie.date.toString()
            movie.genreIds?.forEach { item ->
                genresStr += "  ${Genres.realGenres[item]}"
            }

            if (genresStr.isEmpty()) {
                genresTitleTV.visibility = View.GONE
            } else {
                genresTitleTV.visibility = View.VISIBLE
                genresTV.text = genresStr
            }

            posterIV.loadImage(movie.posterPath.orEmpty())
            favIV.setOnClickListener {
                if (movie.isFav) {
                    favIV.setImageResource(R.drawable.un_fav)
                    viewModel.sendIntend(MovieDetailsIntents.RemoveMovieFromFav)
                } else {
                    favIV.setImageResource(R.drawable.fav)
                    viewModel.sendIntend(MovieDetailsIntents.AddMovieToFav)
                }

            }
        }
    }
}