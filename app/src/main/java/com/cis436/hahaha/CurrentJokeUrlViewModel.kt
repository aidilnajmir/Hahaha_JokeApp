package com.cis436.hahaha

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentJokeUrlViewModel : ViewModel() {

//    private val _url = MutableLiveData<String>()
//    val url : LiveData<String> get() = _url

    private val _jokeUrlCustomization = MutableLiveData<JokeUrlCustomization>(JokeUrlCustomization())
    val jokeUrlCustomization : LiveData<JokeUrlCustomization> get() = _jokeUrlCustomization

    private val _isProgrammingCategorySelected = MutableLiveData<Boolean>()
    val isProgrammingCategorySelected : LiveData<Boolean> get() = _isProgrammingCategorySelected

    private val _isPunCategorySelected = MutableLiveData<Boolean>()
    val isPunCategorySelected : LiveData<Boolean> get() = _isPunCategorySelected

    private val _isSpookyCategorySelected = MutableLiveData<Boolean>()
    val isSpookyCategorySelected : LiveData<Boolean> get() = _isSpookyCategorySelected

    private val _isChristmasCategorySelected = MutableLiveData<Boolean>()
    val isChristmasCategorySelected : LiveData<Boolean> get() = _isChristmasCategorySelected

    private val _isSingleTypeSelected = MutableLiveData<Boolean>()
    val isSingleTypeSelected : LiveData<Boolean> get() = _isSingleTypeSelected

    private val _isTwoPartsTypeSelected = MutableLiveData<Boolean>()
    val isTwoPartsTypeSelected : LiveData<Boolean> get() = _isTwoPartsTypeSelected

    private val _searchString = MutableLiveData<String>()
    val searchString : LiveData<String> get() = _searchString

//    fun setUrl(url: String) {
//        _url.value = url
//    }

    fun updateUrl(jokeUrlCustomization: JokeUrlCustomization) {
        if (jokeUrlCustomization.categories.isNotEmpty()) {
            val categories = listOf("Programming", "Pun", "Spooky", "Christmas").filter {
                jokeUrlCustomization.categories.contains(it)
            }.joinToString(",")

            val types = when {
                jokeUrlCustomization.types.contains("single") && jokeUrlCustomization.types.contains("twopart") -> ""
                jokeUrlCustomization.types.contains("single") -> "?type=single"
                jokeUrlCustomization.types.contains("twopart") -> "?type=twopart"
                else -> ""
            }

            val searchString =
                if (jokeUrlCustomization.searchString.isNotEmpty()) "?contains=${jokeUrlCustomization.searchString}"
                else ""

            val apiUrl = "https://v2.jokeapi.dev/joke/" +
                    categories +
                    types +
                    searchString

            val updatedCustomization = jokeUrlCustomization.copy(apiUrl = apiUrl)
            _jokeUrlCustomization.value = updatedCustomization
            Log.d("URL", "${_jokeUrlCustomization.value}")
        }
    }

    fun setIsProgrammingCategorySelected(isSelected : Boolean) {
        _isProgrammingCategorySelected.value = isSelected
        val customization = _jokeUrlCustomization.value ?: return
        val currentCategories = customization.categories.toMutableList()
        if (isSelected) {
            if (!currentCategories.contains("Programming")) {
                currentCategories.add("Programming")
            }
        } else {
            currentCategories.remove("Programming")
        }
        _jokeUrlCustomization.value = customization.copy(categories = currentCategories)
        updateUrl(_jokeUrlCustomization.value!!)
    }

    fun setIsPunCategorySelected(isSelected : Boolean) {
        _isPunCategorySelected.value = isSelected
        val customization = _jokeUrlCustomization.value ?: return
        val currentCategories = customization.categories.toMutableList()
        if (isSelected) {
            if (!currentCategories.contains("Pun")) {
                currentCategories.add("Pun")
            }
        } else {
            currentCategories.remove("Pun")
        }
        _jokeUrlCustomization.value = customization.copy(categories = currentCategories)
        updateUrl(_jokeUrlCustomization.value!!)
    }

    fun setIsSpookyCategorySelected(isSelected : Boolean) {
        _isSpookyCategorySelected.value = isSelected
        val customization = _jokeUrlCustomization.value ?: return
        val currentCategories = customization.categories.toMutableList()
        if (isSelected) {
            if (!currentCategories.contains("Spooky")) {
                currentCategories.add("Spooky")
            }
        } else {
            currentCategories.remove("Spooky")
        }
        _jokeUrlCustomization.value = customization.copy(categories = currentCategories)
        updateUrl(_jokeUrlCustomization.value!!)
    }

    fun setIsChristmasCategorySelected(isSelected : Boolean) {
        _isChristmasCategorySelected.value = isSelected
        val customization = _jokeUrlCustomization.value ?: return
        val currentCategories = customization.categories.toMutableList()
        if (isSelected) {
            if (!currentCategories.contains("Christmas")) {
                currentCategories.add("Christmas")
            }
        } else {
            currentCategories.remove("Christmas")
        }
        _jokeUrlCustomization.value = customization.copy(categories = currentCategories)
        updateUrl(_jokeUrlCustomization.value!!)
    }

    fun setIsSingleTypeSelected(isSelected : Boolean) {
        _isSingleTypeSelected.value = isSelected
        val customization = _jokeUrlCustomization.value ?: return
        val currentTypes = customization.types.toMutableList()
        if (isSelected) {
            if (!currentTypes.contains("single")) {
                currentTypes.add("single")
            }
        } else {
            currentTypes.remove("single")
        }
        _jokeUrlCustomization.value = customization.copy(types = currentTypes)
        updateUrl(_jokeUrlCustomization.value!!)
    }

    fun setIsTwoPartsTypeSelected(isSelected : Boolean) {
        _isTwoPartsTypeSelected.value = isSelected
        val customization = _jokeUrlCustomization.value ?: return
        val currentTypes = customization.types.toMutableList()
        if (isSelected) {
            if (!currentTypes.contains("twopart")) {
                currentTypes.add("twopart")
            }
        } else {
            currentTypes.remove("twopart")
        }
        _jokeUrlCustomization.value = customization.copy(types = currentTypes)
        updateUrl(_jokeUrlCustomization.value!!)
    }

    fun setSearchString(searchString : String) {
        _searchString.value = searchString
        val customization = _jokeUrlCustomization.value ?: return
        _jokeUrlCustomization.value = customization.copy(searchString = searchString)
        updateUrl(_jokeUrlCustomization.value!!)
    }
}