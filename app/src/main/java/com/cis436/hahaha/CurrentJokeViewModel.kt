package com.cis436.hahaha

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentJokeViewModel : ViewModel() {
    private val _currentJokeId = MutableLiveData<Int>()
    val currentJokeId :LiveData<Int> get() = _currentJokeId

    private val _currentJokeCategory = MutableLiveData<String>()
    val currentJokeCategory :LiveData<String> get() = _currentJokeCategory

    private val _currentJokeType = MutableLiveData<String>()
    val currentJokeType :LiveData<String> get() = _currentJokeType

    private val _currentJokeContentPart1 = MutableLiveData<String>()
    val currentJokeContentPart1 :LiveData<String> get() = _currentJokeContentPart1

    private val _currentJokeContentPart2 = MutableLiveData<String>()
    val currentJokeContentPart2 :LiveData<String> get() = _currentJokeContentPart2

    fun setCurrentJoke(id: Int, category: String, type: String, contentPart1: String, contentPart2: String) {
        _currentJokeId.value = id
        _currentJokeCategory.value = category
        _currentJokeType.value = type
        _currentJokeContentPart1.value = contentPart1
        _currentJokeContentPart2.value = contentPart2
    }
}