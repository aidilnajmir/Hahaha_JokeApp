package com.cis436.hahaha

// This is the data structure of a joke
data class Joke(
    val id: Int,
    val category: String,
    val type: String,
    val contentPart1: String,
    val contentPart2: String = ""
)