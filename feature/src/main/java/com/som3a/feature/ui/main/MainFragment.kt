package com.som3a.feature.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.som3a.base.BaseFragment
import com.som3a.base.EndlessRecyclerOnScrollListener
import com.som3a.common.haveNetworkConnection
import com.som3a.feature.R
import com.som3a.feature.databinding.FragmentMainBinding
import com.som3a.feature.ui.contract.MainContract
import com.som3a.feature.ui.details.DetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener


    private val adapter: MovieAdapter by lazy {
        MovieAdapter { movie ->
            viewModel.setEvent(
                MainContract.Event.OnMovieSelected(
                    movie?.id ?: return@MovieAdapter
                )
            )
            val args = DetailFragmentArgs(id = movie.id).toBundle()
            findNavController().navigate(R.id.detailFragment, args)
        }
    }

    override val createLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun createView(savedInstanceState: Bundle?) {
        binding.rvMovies.adapter = adapter
        initObservers()
        getMoviesList()
        binding.tryAgainButton.setOnClickListener { getMoviesList() }
        endlessRecyclerOnScrollListener = object :
            EndlessRecyclerOnScrollListener(
                (binding.rvMovies.layoutManager as LinearLayoutManager)
            ) {
            override fun onLoadMore() {
                if (!viewModel.isLastPage()) {
                    getMoviesList()
                }
            }
        }
        binding.rvMovies.setOnScrollListener(endlessRecyclerOnScrollListener)
    }

    private fun getMoviesList() =
        viewModel.setEvent(MainContract.Event.OnFetchMoviesList)

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (val state = it.movieState) {
                        is MainContract.MovieState.Idle -> {
                            binding.emptyState.isVisible = false
                            binding.progressBar.isVisible = false
                        }
                        is MainContract.MovieState.Loading -> {
                            binding.emptyState.isVisible = false
                            binding.progressBar.isVisible = true
                        }
                        is MainContract.MovieState.Success -> {
                            val data = state.moviesList
                            adapter.submitList(data)
                            binding.emptyState.isVisible = data.isEmpty()
                            binding.progressBar.isVisible = false
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    when (it) {
                        is MainContract.Effect.ShowError -> {
                            binding.emptyState.isVisible = true
                            if (!requireContext().haveNetworkConnection()) {
                                Toast.makeText(
                                    requireContext(),
                                    "No internet connection, please try again later",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                }
            }
        }
    }
}
