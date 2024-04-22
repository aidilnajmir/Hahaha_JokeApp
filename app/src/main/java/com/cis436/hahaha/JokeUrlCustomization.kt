package com.cis436.hahaha

data class JokeUrlCustomization (
    val apiUrl: String = "https://v2.jokeapi.dev/joke/Programming,Pun,Spooky,Christmas",
    val categories: List<String> = listOf("Programming", "Pun", "Spooky", "Christmas"),
    val types: List<String> = listOf("single", "twopart"),
    val searchString: String = ""
)
