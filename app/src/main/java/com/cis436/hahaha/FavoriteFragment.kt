package com.cis436.hahaha

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cis436.hahaha.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favouriteJokesViewModel: FavoriteJokesViewModel
    private val jokesAdapter = JokesAdapter { joke ->
        favouriteJokesViewModel.removeFavoriteJoke(joke.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteJokesViewModel = ViewModelProvider(requireActivity()).get(FavoriteJokesViewModel::class.java)
        // Setup the RecyclerView with a LinearLayoutManager and the adapter
        binding.favoriteJokesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = jokesAdapter
        }

        // Find the empty image view and text view
        val emptyImageView = binding.emptyImageView
        val emptyTextView = binding.tvNoFavoriteJokesLabel

        // Observe the LiveData from the ViewModel
        favouriteJokesViewModel.favoriteJokes.observe(viewLifecycleOwner) { jokes ->
            // Logging for debugging - can be removed or adjusted as necessary
            jokes.forEach { joke ->
                Log.d("FavoriteJokesViewModel", "Favorite Joke: $joke")
            }
            // Show the empty image view if there are no jokes
            if(jokes.isEmpty()) {
                emptyTextView.visibility = View.VISIBLE
                emptyImageView.visibility =View.VISIBLE
                Glide.with(this)
                    .load(R.drawable.no_favorite)
                    .into(emptyImageView)
            }

            else {
                emptyImageView.visibility =View.GONE
                emptyTextView.visibility = View.GONE
            }

//            emptyTextView.visibility = if (jokes.isEmpty()) View.VISIBLE else View.GONE

            // Submit the new list to the adapter
            jokesAdapter.submitList(jokes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clean up the binding to avoid memory leaks
    }
}