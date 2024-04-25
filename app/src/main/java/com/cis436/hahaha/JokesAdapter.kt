package com.cis436.hahaha

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cis436.hahaha.databinding.ItemJokeBinding

class JokesAdapter(private val onRemoveJoke: (Joke) -> Unit)
    : ListAdapter<Joke, JokesAdapter.JokeViewHolder>(DiffCallback()) {

    // Override the onCreateViewHolder to pass the 'onRemoveJoke' callback to the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val binding = ItemJokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JokeViewHolder(binding, onRemoveJoke)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        // Display the item
        holder.bind(getItem(position))

        // Set a bottom margin for the last item
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.bottomMargin = if (position == itemCount - 1) 50 else 0
        holder.itemView.layoutParams = layoutParams
    }

    // Include callback in the ViewHolder class
    class JokeViewHolder(
        private var binding: ItemJokeBinding,
        private val onRemoveJoke: (Joke) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        // method to display joke's content
        fun bind(joke: Joke) {
            binding.tvJokeContent.text = "\"${joke.contentPart1}\""
            // Display content 2 in italic format
            if (joke.contentPart2.isNotEmpty()) {
                val italicizedText = SpannableString("\n\n\"${joke.contentPart2}\"")
                italicizedText.setSpan(StyleSpan(Typeface.ITALIC), 0, italicizedText.length, 0)
                binding.tvJokeContent.append(italicizedText)
            }

            // Set the click listener for the remove button
            binding.btnRemoveJoke.setOnClickListener {
                onRemoveJoke(joke)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Joke>() {
        override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem == newItem
        }
    }


}