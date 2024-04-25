package com.cis436.hahaha

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
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONObject

// The main fragment is the place where user can get the joke, customize the joke, and save the joke
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    // Use the view models
    private lateinit var currentJokeViewModel: CurrentJokeViewModel
    private lateinit var favouriteJokesViewModel: FavoriteJokesViewModel
    private lateinit var currentJokeUrlViewModel: CurrentJokeUrlViewModel

    // Import the images in an array to be displayed according to the joke's category
    private val categoryImages : Array<Int> = arrayOf(
        R.drawable.programming,
        R.drawable.pun,
        R.drawable.spooky,
        R.drawable.christmas,
        R.drawable.unknown
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set the view models
        currentJokeViewModel = ViewModelProvider(requireActivity()).get(CurrentJokeViewModel::class.java)
        favouriteJokesViewModel = ViewModelProvider(requireActivity()).get(FavoriteJokesViewModel::class.java)
        currentJokeUrlViewModel = ViewModelProvider(requireActivity()).get(CurrentJokeUrlViewModel::class.java)

        // When the user first enter the app, generate a random joke
        if (!currentJokeViewModel.firstJokeGenerated) {
            getRandomJoke()
        }
        // Observe the view model to set the widget's component according to the view model data
        observeViewModel()

        // Call the method to get a new joke based on the current customization
        binding.btnGetJoke.setOnClickListener{
            getRandomJoke()
            observeViewModel()
        }

        // Call the method to save the joke when the user clicks the save button
        binding.btnSaveJoke.setOnClickListener{
            saveJoke()
        }

        // Call the method to show the customization box when the user clicks the customize button
        binding.btnCustomizeJoke.setOnClickListener {
            val customizeJokeFragment = CustomizeJokeFragment()
            customizeJokeFragment.show(parentFragmentManager, "CustomizeJokeFragment")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // method to get a random joke through the api based on the current customization
    private fun getRandomJoke() {
        // Retrieve current url from the customization view model
        var jokeUrl = currentJokeUrlViewModel.jokeUrlCustomization.value?.apiUrl ?: ""

        val queue = Volley.newRequestQueue(requireContext())

        // Make request through GET method of API call to get joke data
        val stringRequest = StringRequest(
            Request.Method.GET, jokeUrl,
            { response ->
                val jsonJoke = JSONObject(response)
                val error = jsonJoke.getString("error")
                // If there is no error, the joke is generated successfully
                if (error == "false") {
                    // Extract the joke's information from the response
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
                    // Create a joke structure to be saved to the view model
                    val joke = Joke(
                        id,
                        category,
                        type,
                        contentPart1,
                        contentPart2,
                    )
                    currentJokeViewModel.setJoke(joke)
                    Log.d("Joke", "${currentJokeViewModel.currentJoke.value}")
                }
                // If there is no joke matches the customization, set the current joke to unknown
                else {
                    val joke = Joke(
                        0,
                        "unknown",
                        "unknown",
                        "No joke available.\"\n\"Please change your customization.",
                        "",
                    )
                    currentJokeViewModel.setJoke(joke)
                    Log.d("MainFragment", "This is not funny: No joke available.")
                }
            },
            // Handle unexpected situation
            {
                Log.e("MainFragment", "This is not funny: Failed to get a joke.")
            })
        queue.add(stringRequest)
    }

    // method to observe the view model to update the widgets' content
    private fun observeViewModel() {
        currentJokeViewModel.currentJoke.observe(viewLifecycleOwner) { joke ->
            // Display the joke's content
            binding.tvContentPart1.text = "\"${joke.contentPart1}\""
            binding.tvContentPart2.text = "\"${joke.contentPart2}\""
            // If the joke only has single part, set the content 2 to invisible
            binding.tvContentPart2.visibility = if (joke.type.contains("twopart")) View.VISIBLE else View.GONE
            // If the joke is not found, set the seve button and label invisible
            binding.saveJokeLabel.visibility = if (joke.category == "unknown") View.GONE else View.VISIBLE
            binding.btnSaveJoke.visibility = if (joke.category == "unknown") View.GONE else View.VISIBLE
            // Display different images for different joke's categories
            when (joke.category) {
                "Programming" -> {
                    Glide.with(this)
                        .load(categoryImages[0])
                        .into(binding.imgJoke)
                }
                "Pun" -> {
                    Glide.with(this)
                        .load(categoryImages[1])
                        .into(binding.imgJoke)
                }
                "Spooky" -> {
                    Glide.with(this)
                        .load(categoryImages[2])
                        .into(binding.imgJoke)
                }
                "Christmas" -> {
                    Glide.with(this)
                        .load(categoryImages[3])
                        .into(binding.imgJoke)
                }
                else -> {
                    Glide.with(this)
                        .load(categoryImages[4])
                        .into(binding.imgJoke)
                }
            }
        }
    }

    // method to add the joke the favorite joke view model
    private fun saveJoke() {
        val joke = currentJokeViewModel.currentJoke.value ?: return
        favouriteJokesViewModel.addFavouriteJoke(joke)
    }
}