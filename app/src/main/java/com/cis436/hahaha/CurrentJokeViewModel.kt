package com.cis436.hahaha

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentJokeViewModel : ViewModel() {

    private val _currentJoke = MutableLiveData<Joke>()
    val currentJoke: LiveData<Joke> get() = _currentJoke
    var firstJokeGenerated = false;

    fun setJoke(joke: Joke) {
        _currentJoke.value = joke
        if (!firstJokeGenerated) {
            firstJokeGenerated = true
        }
    }
}