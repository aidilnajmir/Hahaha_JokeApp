package com.cis436.hahaha

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cis436.hahaha.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var jokesAdapter: JokesAdapter
    private lateinit var favouriteJokesViewModel: FavoriteJokesViewModel

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

        // Initialize the adapter
        jokesAdapter = JokesAdapter()


        // Setup the RecyclerView with a LinearLayoutManager and the adapter
        binding.favoriteJokesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = jokesAdapter
        }

        // Observe the LiveData from the ViewModel
        favouriteJokesViewModel.favoriteJokes.observe(viewLifecycleOwner) { jokes ->

            for (joke in jokes) {
                Log.d("FavoriteJokesViewModel", "Favorite Joke: $joke")
            }
            jokesAdapter.submitList(jokes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up the binding to avoid memory leaks
        _binding = null
    }
}