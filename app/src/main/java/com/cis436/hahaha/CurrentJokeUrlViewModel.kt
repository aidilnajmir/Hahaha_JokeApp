package com.cis436.hahaha

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentJokeUrlViewModel : ViewModel() {

    // Save the joke's customizations information

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

    // method to update the api url when there are any changes in customization
    fun updateUrl(jokeUrlCustomization: JokeUrlCustomization) {
        if (jokeUrlCustomization.categories.isNotEmpty()) {
            // Create a category string to be appended to the url from the current customization
            val categories = listOf("Programming", "Pun", "Spooky", "Christmas", "Misc", "Dark").filter {
                jokeUrlCustomization.categories.contains(it)
            }.joinToString(",")

            // Create the type string to be appended to the url from the current customization
            val types = when {
                // When it has both single and two parts, just assign it an empty string
                jokeUrlCustomization.types.contains("single") && jokeUrlCustomization.types.contains("twopart") -> ""
                jokeUrlCustomization.types.contains("single") -> "?type=single"
                jokeUrlCustomization.types.contains("twopart") -> "?type=twopart"
                else -> ""
            }

            // If type is not an empty string, there may be two query criterias, so the operator is &
            var operator = if (types.isEmpty()) "?" else "&"

            // Create a search string to be appended to the url from the current customization
            val searchString =
                if (jokeUrlCustomization.searchString.isNotEmpty()) "contains=${jokeUrlCustomization.searchString}"
                else ""

            // If there is no search string, we does not need the operator
            if (searchString.isEmpty()) {
                operator = ""
            }

            // Join the strings to form a complete api url based on the current user's customization
            //val apiUrl = "https://v2.jokeapi.dev/joke/Miscellaneous,Dark," +
            val apiUrl = "https://v2.jokeapi.dev/joke/" +
                    categories +
                    types +
                    operator +
                    searchString

            // Update the data
            val updatedCustomization = jokeUrlCustomization.copy(apiUrl = apiUrl)
            _jokeUrlCustomization.value = updatedCustomization
            Log.d("URL", "${_jokeUrlCustomization.value}")
        }
    }

    // method to update attribute and api url when the programming category is selected or deselected
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

    // method to update attribute and api url when the pun category is selected or deselected
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

    // method to update attribute and api url when the spooky category is selected or deselected
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

    // method to update attribute and api url when the christmas category is selected or deselected
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

    // method to update attribute and api url when the single type is selected or deselected
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

    // method to update attribute and api url when the two parts type is selected or deselected
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

    // method to update attribute and api url when the search string is entered
    fun setSearchString(searchString : String) {
        _searchString.value = searchString
        val customization = _jokeUrlCustomization.value ?: return
        _jokeUrlCustomization.value = customization.copy(searchString = searchString)
        updateUrl(_jokeUrlCustomization.value!!)
    }

    // method to check if the user selects at least one category
    fun isAtLeastOneCategorySelected(): Boolean {
        return _isProgrammingCategorySelected.value == true ||
                _isPunCategorySelected.value == true ||
                _isSpookyCategorySelected.value == true ||
                _isChristmasCategorySelected.value == true
    }

    // method to check if the user selects at least one type
    fun isAtLeastOneTypeSelected(): Boolean {
        return _isSingleTypeSelected.value == true ||
                _isTwoPartsTypeSelected.value == true
    }
}