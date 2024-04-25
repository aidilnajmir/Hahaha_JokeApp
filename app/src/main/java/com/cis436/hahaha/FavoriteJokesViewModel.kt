package com.cis436.hahaha

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// This view model stores a list of saved jokes
class FavoriteJokesViewModel : ViewModel() {
    private val _favoriteJokes = MutableLiveData<List<Joke>>(emptyList())
    val favoriteJokes: LiveData<List<Joke>>
        get() = _favoriteJokes

    // Add a joke to the list
    fun addFavouriteJoke(joke: Joke) {
        val currentList = _favoriteJokes.value ?: emptyList()
        if (!currentList.any { it.id == joke.id }) {
            _favoriteJokes.value = currentList + joke
        }
    }

    // Remove a joke from the current list
    fun removeFavoriteJoke(jokeId: Int) {
        val currentList = _favoriteJokes.value ?: return
        _favoriteJokes.value = currentList.filterNot { it.id == jokeId }.also {
            Log.d("FavoriteJokesViewModel", "Updated favorites list: $it")
        }
    }

}
