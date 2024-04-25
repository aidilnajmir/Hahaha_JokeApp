package com.cis436.hahaha

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cis436.hahaha.databinding.FragmentCustomizeJokeBinding
import com.cis436.hahaha.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar

// The dialog fragment is shown to the user to select criteria for joke customization
class CustomizeJokeFragment : DialogFragment() {
    private var _binding: FragmentCustomizeJokeBinding? = null
    private val binding get() = _binding!!

    // Use the view model of current joke and current customization
    private lateinit var currentJokeUrlViewModel: CurrentJokeUrlViewModel
    private lateinit var jokeUrlCustomization : JokeUrlCustomization

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomizeJokeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentJokeUrlViewModel = ViewModelProvider(requireActivity()).get(CurrentJokeUrlViewModel::class.java)
        // Get the current customization from the view model
        jokeUrlCustomization = currentJokeUrlViewModel.jokeUrlCustomization.value ?: JokeUrlCustomization()
        // Observe the view model to set the checkboxes and string input to the current customization
        observeViewModel()
        // Handle the changes made by user
        customizeJoke()
    }

    // method to update the changes made by user to the view model
    private fun customizeJoke() {
        // Update view model when there is a change on programming checkbox
        binding.checkBoxProgramming.setOnCheckedChangeListener { buttonView, isChecked ->
            currentJokeUrlViewModel.setIsProgrammingCategorySelected(isChecked)
        }

        // Update view model when there is a change on pun checkbox
        binding.checkBoxPun.setOnCheckedChangeListener { buttonView, isChecked ->
            currentJokeUrlViewModel.setIsPunCategorySelected(isChecked)
        }

        // Update view model when there is a change on spooky checkbox
        binding.checkBoxSpooky.setOnCheckedChangeListener { buttonView, isChecked ->
            currentJokeUrlViewModel.setIsSpookyCategorySelected(isChecked)
        }

        // Update view model when there is a change on christmas checkbox
        binding.checkBoxChristmas.setOnCheckedChangeListener { buttonView, isChecked ->
            currentJokeUrlViewModel.setIsChristmasCategorySelected(isChecked)
        }

        // Update view model when there is a change on single checkbox
        binding.checkBoxSingle.setOnCheckedChangeListener { buttonView, isChecked ->
            currentJokeUrlViewModel.setIsSingleTypeSelected(isChecked)
        }

        // Update view model when there is a change on two-part checkbox
        binding.checkBoxTwoParts.setOnCheckedChangeListener { buttonView, isChecked ->
            currentJokeUrlViewModel.setIsTwoPartsTypeSelected(isChecked)
        }

        // Update view model when there is a change on search string
        binding.etSearch.addTextChangedListener {
            currentJokeUrlViewModel.setSearchString(it.toString())
        }
        binding.etSearch.setText(jokeUrlCustomization.searchString)

        // button to exit from the customization box
        binding.btnOK.setOnClickListener {
            // If at least one category and at least one type is selected, close the box
            if (currentJokeUrlViewModel.isAtLeastOneCategorySelected() && currentJokeUrlViewModel.isAtLeastOneTypeSelected()) {
                dismiss()
            }
            // Display a snackbar is category or type are empty
            else {
                view?.let { view ->
                    view?.let { view ->
                        val snackbar = Snackbar.make(view, "Blank is not funny.\nPlease select at least one\ncategory and type.", Snackbar.LENGTH_SHORT)
                        snackbar.setAction("OK") {
                        }
                        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.maxLines = 5
                        snackbar.show()
                    }
                }
            }
        }
    }

    // Observe the view model and update the widgets' content when there are any changes
    private fun observeViewModel() {
        currentJokeUrlViewModel.jokeUrlCustomization.observe(viewLifecycleOwner){ jokeUrlCustomization ->
            binding.checkBoxProgramming.isChecked = jokeUrlCustomization.categories.contains("Programming")
            binding.checkBoxPun.isChecked = jokeUrlCustomization.categories.contains("Pun")
            binding.checkBoxSpooky.isChecked = jokeUrlCustomization.categories.contains("Spooky")
            binding.checkBoxChristmas.isChecked = jokeUrlCustomization.categories.contains("Christmas")

            binding.checkBoxSingle.isChecked = jokeUrlCustomization.types.contains("single")
            binding.checkBoxTwoParts.isChecked = jokeUrlCustomization.types.contains("twopart")
        }
    }
}