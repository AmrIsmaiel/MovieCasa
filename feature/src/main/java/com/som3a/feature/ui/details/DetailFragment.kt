package com.som3a.feature.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.som3a.base.BaseFragment
import com.som3a.feature.databinding.FragmentDetailsBinding
import com.som3a.feature.model.MovieDetailsUiModel
import com.som3a.feature.ui.contract.DetailsContract
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailsBinding>() {

    private val mViewModel: MovieDetailsViewModel by viewModels()
    private val args by navArgs<DetailFragmentArgs>()
    override val createLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding
        get() = FragmentDetailsBinding::inflate

    override fun createView(savedInstanceState: Bundle?) {
        initObservers()
        getMoviesList()
        binding.tryAgainButton.setOnClickListener { getMoviesList() }
        binding.backButton.setOnClickListener{ findNavController().popBackStack() }
    }

    private fun getMoviesList() =
        mViewModel.setEvent(DetailsContract.Event.OnFetchMovie(args.id))

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.collect {
                    when (val state = it.movieState) {
                        is DetailsContract.MovieState.Idle -> {
                            binding.progressBar.isVisible = false
                            binding.emptyState.isVisible = false
                        }
                        is DetailsContract.MovieState.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.dataLayout.isVisible = false
                        }
                        is DetailsContract.MovieState.Success -> {
                            binding.dataLayout.isVisible = true
                            val data = state.movie
                            setData(data)
                            binding.progressBar.isVisible = false
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.effect.collect {
                    when (it) {
                        is DetailsContract.Effect.ShowError -> {
                            binding.emptyState.isVisible = true
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setData(selectedMovie: MovieDetailsUiModel) {
        with(binding) {
            ivImage.apply {
                Glide.with(this)
                    .load(selectedMovie.posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(android.R.drawable.stat_notify_error)
                    .into(this)
            }

            titleTextView.text = selectedMovie.title
            backButton.setOnClickListener { requireActivity().onBackPressed() }
            overViewTextView.text = selectedMovie.overview
            releaseDateTextView.text = selectedMovie.releaseDate
            productionCompaniesTextView.text = buildSpannedString {
                bold {
                    append("Production companies: ")
                }
                append(selectedMovie.productionCompanies?.joinToString())
            }
        }
    }


}