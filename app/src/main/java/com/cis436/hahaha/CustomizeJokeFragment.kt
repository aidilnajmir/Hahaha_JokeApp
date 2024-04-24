package com.cis436.hahaha

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cis436.hahaha.databinding.FragmentCustomizeJokeBinding
import com.cis436.hahaha.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar

class CustomizeJokeFragment : DialogFragment() {
    private var _binding: FragmentCustomizeJokeBinding? = null
    private val binding get() = _binding!!
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
        jokeUrlCustomization = currentJokeUrlViewModel.jokeUrlCustomization.value ?: JokeUrlCustomization()
        observeViewModel()
        customizeJoke()
    }

    private fun customizeJoke() {
        binding.checkBoxProgramming.setOnCheckedChangeListener { buttonView, isChecked ->
            currentJokeUrlViewModel.setIsProgrammingCategorySelected(isChecked)
        }

        binding.checkBoxPun.setOnCheckedChangeListener { buttonView, isChecked ->
            currentJokeUrlViewModel.setIsPunCategorySelected(isChecked)
        }

        binding.checkBoxSpooky.setOnCheckedChangeListener { buttonView, isChecked ->
            currentJokeUrlViewModel.setIsSpookyCategorySelected(isChecked)
        }

        binding.checkBoxChristmas.setOnCheckedChangeListener { buttonView, isChecked ->
            currentJokeUrlViewModel.setIsChristmasCategorySelected(isChecked)
        }

        binding.checkBoxSingle.setOnCheckedChangeListener { buttonView, isChecked ->
            currentJokeUrlViewModel.setIsSingleTypeSelected(isChecked)
        }

        binding.checkBoxTwoParts.setOnCheckedChangeListener { buttonView, isChecked ->
            currentJokeUrlViewModel.setIsTwoPartsTypeSelected(isChecked)
        }

        binding.etSearch.addTextChangedListener {
            currentJokeUrlViewModel.setSearchString(it.toString())
        }

        binding.etSearch.setText(jokeUrlCustomization.searchString)

        binding.btnOK.setOnClickListener {
            if (currentJokeUrlViewModel.isAtLeastOneCategorySelected() && currentJokeUrlViewModel.isAtLeastOneTypeSelected()) {
                dismiss()
            } else {
                view?.let { view ->
                    Snackbar.make(view, "Please select at least one category and at least one type", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

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