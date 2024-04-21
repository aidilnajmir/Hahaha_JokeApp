package com.cis436.hahaha

import android.R
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cis436.hahaha.databinding.FragmentMainBinding
import org.json.JSONArray
import org.json.JSONObject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentJokeViewModel: CurrentJokeViewModel
    private lateinit var favouriteJokesViewModel: FavoriteJokesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentJokeViewModel = ViewModelProvider(this).get(CurrentJokeViewModel::class.java)
        favouriteJokesViewModel = ViewModelProvider(this).get(FavoriteJokesViewModel::class.java)
        getRandomJoke()
        observeViewModel()

        binding.btnGetJoke.setOnClickListener{
            getRandomJoke()
            observeViewModel()
        }

        binding.btnSaveJoke.setOnClickListener{
            saveJoke()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getRandomJoke() {
        // Define url to get cat data
        var jokeUrl = "https://v2.jokeapi.dev/joke/Any"

        val queue = Volley.newRequestQueue(requireContext())

        // Make request through GET method of API call to get cat data
        val stringRequest = StringRequest(
            Request.Method.GET, jokeUrl,
            { response ->
                val jsonJoke = JSONObject(response)
                val id = jsonJoke.getInt("id")
                val category = jsonJoke.getString("category")
                val type = jsonJoke.getString("type")
                var contentPart1 = ""
                var contentPart2 = ""
                if (type == "single") {
                    contentPart1 = jsonJoke.getString("joke")
                } else {
                    contentPart1 = jsonJoke.getString("setup")
                    contentPart2 = jsonJoke.getString("delivery")
                }
                val joke = Joke(
                    id,
                    category,
                    type,
                    contentPart1,
                    contentPart2,
                )
                currentJokeViewModel.setJoke(joke)
                Log.d("MainFragment", "Joke Id: $id")
                Log.d("MainFragment", "Joke Category: $category")
                Log.d("MainFragment", "Joke Type: $type")
                Log.d("MainFragment", "Joke Part 1: $contentPart1")
                Log.d("MainFragment", "Joke Part 2: $contentPart2")
            },
            {
                Log.e("MainFragment", "This is not funny: Failed to get a joke.")
            })
        queue.add(stringRequest)
    }

    private fun observeViewModel() {
        currentJokeViewModel.currentJoke.observe(viewLifecycleOwner) { joke ->
            binding.tvContentPart1.text = joke.contentPart1
            binding.tvContentPart2.text = joke.contentPart2
        }
    }

    private fun saveJoke() {
        currentJokeViewModel.currentJoke.observe(viewLifecycleOwner) { joke ->
            favouriteJokesViewModel.addFavoriteJoke(joke)
        }
    }

}