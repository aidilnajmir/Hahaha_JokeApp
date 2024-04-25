package com.cis436.hahaha

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentJokeViewModel : ViewModel() {

    // Store the current generated joke
    private val _currentJoke = MutableLiveData<Joke>()
    val currentJoke: LiveData<Joke> get() = _currentJoke

    // To track if the first joke is generated for the first time that user is on the main screen
    var firstJokeGenerated = false;

    // method to set the current joke to the model once a new joke is generated
    fun setJoke(joke: Joke) {
        _currentJoke.value = joke
        // Set the flag to true once the first joke is generated
        if (!firstJokeGenerated) {
            firstJokeGenerated = true
        }
    }
}