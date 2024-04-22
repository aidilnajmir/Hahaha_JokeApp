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
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleObserver
import org.json.JSONArray
import org.json.JSONObject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentJokeViewModel: CurrentJokeViewModel
    private lateinit var favouriteJokesViewModel: FavoriteJokesViewModel
    private lateinit var currentJokeUrlViewModel: CurrentJokeUrlViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentJokeViewModel = ViewModelProvider(requireActivity()).get(CurrentJokeViewModel::class.java)
        favouriteJokesViewModel = ViewModelProvider(requireActivity()).get(FavoriteJokesViewModel::class.java)
        currentJokeUrlViewModel = ViewModelProvider(requireActivity()).get(CurrentJokeUrlViewModel::class.java)

        if (!currentJokeViewModel.firstJokeGenerated) {
            getRandomJoke()
        }
        observeViewModel()

        binding.btnGetJoke.setOnClickListener{
            getRandomJoke()
            observeViewModel()
        }

        binding.btnSaveJoke.setOnClickListener{
            saveJoke()
        }

        binding.btnCustomizeJoke.setOnClickListener {
            val customizeJokeFragment = CustomizeJokeFragment()
            customizeJokeFragment.show(parentFragmentManager, "CustomizeJokeFragment")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getRandomJoke() {
        // Define url to get cat data
        var jokeUrl = currentJokeUrlViewModel.jokeUrlCustomization.value?.apiUrl ?: ""

//        currentJokeUrlViewModel.jokeUrlCustomization.observe(viewLifecycleOwner) { jokeUrlCustomization ->
//            jokeUrl = jokeUrlCustomization.apiUrl
//        }

        val queue = Volley.newRequestQueue(requireContext())

        // Make request through GET method of API call to get cat data
        val stringRequest = StringRequest(
            Request.Method.GET, jokeUrl,
            { response ->
                val jsonJoke = JSONObject(response)
                val error = jsonJoke.getString("error")
                var contentPart1 = ""
                var contentPart2 = ""
                if (error == "false") {
                    val id = jsonJoke.getInt("id")
                    val category = jsonJoke.getString("category")
                    val type = jsonJoke.getString("type")
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
//                Log.d("MainFragment", "Joke Id: $id")
                    Log.d("Has error?", "$error")
//                Log.d("MainFragment", "Joke Category: $category")
                    //               Log.d("MainFragment", "Joke Type: $type")
//                Log.d("MainFragment", "Joke Part 1: $contentPart1")
//                Log.d("MainFragment", "Joke Part 2: $contentPart2")
                }
                else {
                    val joke = Joke(
                        0,
                        "unknown",
                        "unknown",
                        "No joke available.",
                        "Please revise your customization.",
                    )
                    currentJokeViewModel.setJoke(joke)
                    Log.e("MainFragment", "This is not funny: No joke available.")
                }

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
            binding.saveJokeLabel.visibility = if (joke.category == "unknown") View.GONE else View.VISIBLE
            binding.btnSaveJoke.visibility = if (joke.category == "unknown") View.GONE else View.VISIBLE
        }
    }

    private fun saveJoke() {
        val joke = currentJokeViewModel.currentJoke.value ?: return
        favouriteJokesViewModel.addFavouriteJoke(joke)
    }

}