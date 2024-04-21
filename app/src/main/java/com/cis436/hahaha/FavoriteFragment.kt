package com.cis436.hahaha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cis436.hahaha.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteJokesViewModel by viewModels()
    private var _binding: FragmentFavoriteBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var jokesAdapter: JokesAdapter

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

        // Initialize the adapter
        jokesAdapter = JokesAdapter()

        // Setup the RecyclerView with a LinearLayoutManager and the adapter
        binding.favoriteJokesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = jokesAdapter
        }

        // Observe the LiveData from the ViewModel
        viewModel.favoriteJokes.observe(viewLifecycleOwner) { jokes ->
            jokesAdapter.submitList(jokes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up the binding to avoid memory leaks
        _binding = null
    }
}