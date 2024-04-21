package com.cis436.hahaha

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Joke(
    val id: Int,
    val category: String,
    val type: String,
    val contentPart1: String,
    val contentPart2: String = ""
)

class FavoriteJokesViewModel : ViewModel() {
    private val _favoriteJokes = MutableLiveData<List<Joke>>()
    val favoriteJokes: LiveData<List<Joke>>
        get() = _favoriteJokes

    fun addFavouriteJoke(joke: Joke) {
        val currentList = _favoriteJokes.value ?: emptyList()
        _favoriteJokes.value = currentList + joke
    }

    fun removeFavoriteJoke(jokeId: Int) {
        val currentList = _favoriteJokes.value ?: return
        _favoriteJokes.value = currentList.filterNot { it.id == jokeId }
    }
}
