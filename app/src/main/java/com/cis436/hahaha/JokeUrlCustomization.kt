package com.cis436.hahaha

data class JokeUrlCustomization (
    val apiUrl: String = "https://v2.jokeapi.dev/joke/Programming,Pun,Spooky,Christmas",
    //val apiUrl: String = "https://v2.jokeapi.dev/joke/Programming,Miscellaneous,Dark,Pun,Spooky,Christmas",
    val categories: List<String> = listOf("Programming", "Pun", "Spooky", "Christmas"),
    //val categories: List<String> = listOf("Programming", "Pun", "Spooky", "Christmas", "Misc", "Dark"),
    val types: List<String> = listOf("single", "twopart"),
    val searchString: String = ""
)
